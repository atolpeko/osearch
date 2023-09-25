package com.osearch.indexer.adapter.out.repository;

import com.osearch.indexer.application.exception.DataAccessException;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.domain.entity.Page;
import com.osearch.indexer.domain.entity.Topic;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Values;

@Log4j2
@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository {
    private final Driver driver;

    @Override
    public Long save(Page page) {
        try (var session = driver.session()) {
            var savedId = session.writeTransaction(transaction -> {
                var id = savePage(transaction, page);
                saveTopics(transaction, id, page.getTopics());
                saveReferredPages(transaction, id, page.getNestedUrls());
                return id;
            });

            log.debug("Page with URL {} is saved. ID: {}", page.getUrl(), savedId);
            return savedId;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private Long savePage(Transaction transaction, Page page) {
        var query = "MERGE (p:Page{url: $url}) "
            + "SET p.title = $title, p.loadTime = $loadTime, p.isIndexed = TRUE "
            + "RETURN ID(p) AS id";

        log.debug("Saving page with URL {}", page.getUrl());
        var result = transaction.run(query, Values.parameters(
            "url", page.getUrl(),
            "title", page.getTitle(),
            "loadTime", page.getLoadTime()
        ));

        return result.single().get("id").asLong();
    }

    private void saveTopics(Transaction transaction, Long pageId, Set<Topic> topics) {
        var mergeTopics = "MATCH (p:Page) WHERE id(p) = $pageId "
            + "UNWIND $topics as topic "
            + "MERGE (t:Topic{name: topic.name})";
        var mergeRelationships = "MATCH (p:Page) WHERE id(p) = $pageId "
            + "UNWIND $topics as topic "
            + "MATCH (t:Topic{name: topic.name}) "
            + "MERGE (p)-[:HAS{significance: topic.significance}]->(t)";

        var topicParams = topics.stream()
            .map(topic -> Values.parameters("name", topic.toString(),
                "significance", topic.getSignificance().getValue()))
            .collect(Collectors.toList());

        log.debug("Saving {} topics for page with ID {}", topics.size(), pageId);
        var params = Values.parameters("pageId", pageId, "topics", topicParams);
        transaction.run(mergeTopics, params);
        transaction.run(mergeRelationships, params);
    }

    private void saveReferredPages(Transaction transaction, Long pageId, Set<String> urls) {
        var mergePage = "MATCH (root:Page) WHERE id(root) = $id "
            + "UNWIND $urls as url "
            + "MERGE (p:Page{url: url}) ON CREATE SET p.isIndexed = FALSE";
        var mergeRelationship = "MATCH (root:Page) WHERE id(root) = $id "
            + "UNWIND $urls as url "
            + "MATCH (p:Page{url: url}) "
            + "MERGE (root)-[:REFERS_TO]->(p)";

        log.debug("Saving {} referred pages for page with ID {}", urls.size(), pageId);
        var params = Values.parameters("id", pageId, "urls", urls);
        transaction.run(mergePage, params);
        transaction.run(mergeRelationship, params);
    }

    @Override
    public int countIndexed() {
        try (var session = driver.session()) {
            return session.readTransaction(transaction -> {
                var query = "MATCH(p:Page{isIndexed: True}) "
                    + "RETURN COUNT(*) AS count";
                var result = transaction.run(query);
                return result.single().get("count", 0);
            });
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
