package com.osearch.ranker.adapter.out;

import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.domain.entity.Keyword;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Node;

@Log4j2
@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository {
    private final Driver driver;

    @Override
    public Optional<Page> findById(long id) {
        try (var session = driver.session()) {
            return session.readTransaction(transaction -> {
                var query = "MATCH (p:Page), "
                    + "(p)-[:REFERS_TO]->(rp:Page), "
                    + "(p)-[h:HAS]->(k:Keyword) "
                    + "WHERE ID(p) = $id "
                    + "RETURN p as page, "
                    + "ID(p) as page_id, "
                    + "COLLECT(rp) as referred, "
                    + "COLLECT(k.value) as keywords, "
                    + "COLLECT(h.occurrences) as occurrences";

                var result = transaction.run(query, Values.parameters("id", id));
                if (result.hasNext()) {
                    return mapResult(result.single());
                } else {
                    return Optional.empty();
                }
            });
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private Optional<Page> mapResult(Record record) {
        var page = mapPage(record);
        if (page == null) {
            return Optional.empty();
        }

        var referred = record.get("referred").asList(Value::asNode).stream()
            .map(this::mapReferredPage)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        var keywordValues = record.get("keywords").asList(Value::asString);
        var occurrences = record.get("occurrences").asList(Value::asLong);
        var keywords = IntStream.range(0, keywordValues.size())
            .mapToObj(i -> new Keyword(keywordValues.get(i), occurrences.get(i)))
            .collect(Collectors.toSet());


        page.setKeywords(keywords);
        page.setReferredPages(new HashSet<>(referred));
        return Optional.of(page);
    }

    private Page mapPage(Record record) {
        var isIndexed = record.get("page").get("isIndexed").asBoolean();
        if (!isIndexed) {
            return null;
        }

        var loadTime = Duration.ofNanos(record.get("page").asNode()
            .get("loadTime").asIsoDuration().nanoseconds());
        return Page.builder()
            .sourceId(record.get("page_id").asLong())
            .url(record.get("page").asNode().get("url").asString())
            .title(record.get("page").asNode().get("title").asString())
            .loadTime(loadTime)
            .isIndexed(true)
            .build();
    }

    private Page mapReferredPage(Node node) {
        var isIndexed = node.get("isIndexed").asBoolean();
        if (!isIndexed) {
          return null;
        }

        return Page.builder()
            .url(node.get("url").asString())
            .isIndexed(true)
            .build();
    }
}
