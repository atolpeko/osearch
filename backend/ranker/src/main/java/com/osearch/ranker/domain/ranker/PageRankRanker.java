package com.osearch.ranker.domain.ranker;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.domain.properties.DomainProperties;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * PageRankRanker is a class that implements the ranking algorithm based on page rank.
 */
@Log4j2
@RequiredArgsConstructor
public class PageRankRanker extends BaseRanker {
    private final DomainProperties properties;

    @Override
    public void rank(Index index) {
        var iterations = properties.getPageRankIterations();
        var dampingFactor = properties.getPageRankDampingFactor();
        setInitialRanks(index.getPages());
        calculatePageRanks(index.getPages(), iterations, dampingFactor);

        log.debug("{} pages ranked by page rank for index {}",
            index.getPages().size(), index.getTopic());
        next(index);
    }

    private void setInitialRanks(Set<Page> pages) {
        var allPages = pages.stream()
            .map(page -> Set.of(page.getReferredPages(), Set.of(page)))
            .flatMap(Set::stream)
            .flatMap(Set::stream)
            .collect(Collectors.toList());

        var initialRank = (double) 1 / allPages.size();
        allPages.forEach(page -> page.setPageRank(initialRank));
    }

    private void calculatePageRanks(Set<Page> pages, int iterations, double dampingFactor) {
        for (var i = 0; i < iterations; i++) {
            for (var page : pages) {
                double sum = 0.0;
                for (var otherPage : pages) {
                    var refersTo = otherPage.getReferredPages();
                    if (otherPage != page && refersTo.contains(page)) {
                        sum += otherPage.getPageRank() / refersTo.size();
                    }
                }

                var rank = (1 - dampingFactor) / pages.size() + dampingFactor * sum;
                page.setPageRank(rank);
            }
        }
    }
}
