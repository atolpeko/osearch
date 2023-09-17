package com.osearch.ranker.domain.service.ranker;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.properties.DomainProperties;
import com.osearch.ranker.domain.service.ranker.impl.Ranker;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class RankerServiceImpl implements RankerService {
    private final List<Ranker> rankers;
    private final DomainProperties properties;

    @Override
    public void rank(Index index) {
        log.debug("Ranking {} pages using {} rankers", index.getPages().size(), rankers.size());
        rankers.forEach(ranker -> ranker.rank(index));
        index.getPages().forEach(page -> {
            var totalRank = (page.getKeywordRank() * properties.getKeywordRankWeight() +
                page.getPageRank() * properties.getPageRankWeight() +
                page.getMetaRank() * properties.getMetaRankWeight()) /
                (properties.getPageRankWeight() + properties.getKeywordRankWeight() +
                    properties.getMetaRankWeight());
            page.setTotalRank(totalRank);
        });
    }
}
