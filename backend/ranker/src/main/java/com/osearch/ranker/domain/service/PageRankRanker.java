package com.osearch.ranker.domain.service;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.domain.properties.DomainProperties;

import java.util.Set;

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
        calculatePageRanks(index.getPages(), iterations, dampingFactor);

        log.debug("{} pages ranked by page rank for index {}",
            index.getPages().size(), index.getTopic());
        next(index);
    }

    public void calculatePageRanks(Set<Page> pages, int iterations, double dampingFactor) {
        for (var i = 0; i < iterations; i++) {
            for (var page : pages) {
                double sum = 0.0;
                for (var otherPage : pages) {
                    var refersTo = otherPage.getReferredPages();
                    if (otherPage != page && refersTo.contains(page)) {
                        sum += otherPage.getPageRank() / refersTo.size();
                    }
                }

                var rank = (1 - dampingFactor) + dampingFactor * sum;
                page.setPageRank(rank);
            }
        }
    }
}
