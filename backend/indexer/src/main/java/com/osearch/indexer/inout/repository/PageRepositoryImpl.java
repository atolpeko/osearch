package com.osearch.indexer.inout.repository;

import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Values;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class PageRepositoryImpl implements PageRepository {
    private final Driver driver;

    @Override
    public Long save(Page page) {
        try (var session = driver.session()) {
            var savedId = session.writeTransaction(transaction -> {
                var id = savePage(transaction, page);
                saveKeywords(transaction, id, page.getKeywords());
                saveReferredPages(transaction, id, page.getNestedUrls());
                return id;
            });

            log.debug("Page with URL {} is saved. ID: {}", page.getUrl(), savedId);
            return savedId;
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

    private void saveKeywords(Transaction transaction, Long pageId, Set<Keyword> keywords) {
        var mergeKeywords = "MATCH (p:Page) WHERE id(p) = $pageId "
                + "UNWIND $keywords as keyword "
                + "MERGE (k:Keyword{value: keyword.value})";
        var mergeRelationships = "MATCH (p:Page) WHERE id(p) = $pageId "
                + "UNWIND $keywords as keyword "
                + "MATCH (k:Keyword{value: keyword.value}) "
                + "MERGE (p)-[:HAS{occurrences: keyword.occurrences}]->(k)";

        var unwindKeywords = keywords.stream()
                .map(keyword -> Values.parameters("value", keyword.getValue(),
                        "occurrences", keyword.getOccurrences()))
                .collect(Collectors.toList());

        log.debug("Saving {} keywords for page with ID {}", keywords.size(), pageId);
        var params = Values.parameters("pageId", pageId, "keywords", unwindKeywords);
        transaction.run(mergeKeywords, params);
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
}
