package com.osearch.ranker.adapter.out;

import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.domain.entity.Keyword;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.HashSet;
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
                    + "COLLECT(rp) as referred, "
                    + "COLLECT(k.value) as keywords, "
                    + "COLLECT(h.occurrences) as occurrences";

                var result = transaction.run(query, Values.parameters("id", id));
                if (result.hasNext()) {
                    return Optional.of(mapResult(result.single()));
                } else {
                    return Optional.empty();
                }
            });
        }
    }

    private Page mapResult(Record record) {
        var referred = record.get("referred").asList(Value::asNode).stream()
            .map(this::mapPage)
            .collect(Collectors.toSet());
        var keywordValues = record.get("keywords").asList(Value::asString);
        var occurrences = record.get("occurrences").asList(Value::asLong);
        var keywords = IntStream.range(0, keywordValues.size())
            .mapToObj(i -> new Keyword(keywordValues.get(i), occurrences.get(i)))
            .collect(Collectors.toSet());

        var page = mapPage(record.get("page").asNode());
        page.setKeywords(keywords);
        page.setReferredPages(new HashSet<>(referred));
        return page;
    }

    private Page mapPage(Node node) {
        var isIndexed = node.get("isIndexed").asBoolean();
        if (!isIndexed) {
          return Page.builder().url(node.get("url").asString()).build();
        }

        return Page.builder()
            .url(node.get("url").asString())
            .title(node.get("title").asString())
            .loadTime(Duration.ofNanos(node.get("loadTime").asIsoDuration().nanoseconds()))
            .isIndexed(true)
            .build();

    }
}
