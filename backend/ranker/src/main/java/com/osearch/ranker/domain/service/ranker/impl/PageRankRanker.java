package com.osearch.ranker.domain.service.ranker.impl;

import static com.osearch.ranker.util.DurationLogger.withDurationLog;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.domain.properties.DomainProperties;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

@Log4j2
@RequiredArgsConstructor
public class PageRankRanker implements Ranker {
    private final DomainProperties properties;

    @Override
    public void rank(Index index) {
        var pages = index.getPages();
        Runnable task = () -> {
            log.debug("Ranking {} pages by page rank", pages.size());
            var iterations = properties.getPageRankIterations();
            var dampingFactor = properties.getPageRankDampingFactor();
            calculatePageRanks(pages, iterations, dampingFactor);
        };

        withDurationLog(task, pages.size() + " pages ranked using page rank", Level.DEBUG);
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
