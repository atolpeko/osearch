package com.osearch.ranker.domain.ranker;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.properties.DomainProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * The FinalRanker class is responsible for ranking pages based on their total rank.
 */
@Log4j2
@RequiredArgsConstructor
public class FinalRanker extends BaseRanker {
    private final DomainProperties properties;

    @Override
    public void rank(Index index) {
        index.getPages().forEach(page -> {
            var totalRank = (page.getTopicRank() * properties.getTopicRankWeight() +
                page.getPageRank() * properties.getPageRankWeight() +
                page.getMetaRank() * properties.getMetaRankWeight()) /
                (properties.getPageRankWeight() + properties.getTopicRankWeight() +
                    properties.getMetaRankWeight());
            page.setTotalRank(totalRank);
        });

        log.debug("{} pages ranked by total rank for index {}",
            index.getPages().size(), index.getTopic());
        next(index);
    }
}
