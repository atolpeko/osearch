package com.osearch.ranker.adapter.out;

import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.domain.entity.Topic;
import com.osearch.ranker.domain.valueobject.Significance;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Node;

/**
 * Implements the PageRepository that works with a graph database using Bolt driver.
 */
@Log4j2
@RequiredArgsConstructor
public class PageGraphRepository implements PageRepository {
    private final Driver driver;

    @Override
    public Optional<Page> findById(long id) {
        try (var session = driver.session()) {
            return session.readTransaction(transaction -> {
                var query = "MATCH (p:Page), "
                    + "(p)-[:REFERS_TO]->(rp:Page), "
                    + "(p)-[h:HAS]->(t:Topic) "
                    + "WHERE ID(p) = $id "
                    + "RETURN p as page, "
                    + "ID(p) as page_id, "
                    + "COLLECT(rp) as referred, "
                    + "COLLECT(t.name) as topics, "
                    + "COLLECT(h.significance) as significances";

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

    private Optional<Page> mapResult(Record dataRecord) {
        var page = mapPage(dataRecord);
        if (page == null) {
            return Optional.empty();
        }

        var referred = dataRecord.get("referred").asList(Value::asNode).stream()
            .map(this::mapReferredPage)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        var topics = mapTopics(dataRecord);

        page.setTopics(topics);
        page.setReferredPages(new HashSet<>(referred));
        return Optional.of(page);
    }

    private Page mapPage(Record dataRecord) {
        var isIndexed = dataRecord.get("page").get("isIndexed").asBoolean();
        if (!isIndexed) {
            return null;
        }

        var loadTime = Duration.ofNanos(dataRecord.get("page").asNode()
            .get("loadTime").asIsoDuration().nanoseconds());
        return Page.builder()
            .sourceId(dataRecord.get("page_id").asLong())
            .url(dataRecord.get("page").asNode().get("url").asString())
            .title(dataRecord.get("page").asNode().get("title").asString())
            .loadTime(loadTime)
            .isIndexed(true)
            .build();
    }

    private Set<Topic> mapTopics(Record dataRecord) {
        var topicNames = dataRecord.get("topics").asList(Value::asString);
        var topicSignificances = dataRecord.get("significances")
            .asList(Value::asDouble)
            .stream()
            .map(Significance::new)
            .collect(Collectors.toList());
        return IntStream.range(0, topicNames.size())
            .mapToObj(i -> new Topic(topicNames.get(i), topicSignificances.get(i)))
            .collect(Collectors.toSet());
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
