package com.osearch.ranker.domain.ranker;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.util.Comparator;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * MetaRanker is a class that ranks pages of an index based on their meta information.
 */
@Log4j2
@RequiredArgsConstructor
public class MetaRanker extends BaseRanker {

    @Override
    public void rank(Index index) {
        var sorted = index.getPages().stream()
            .sorted(Comparator.comparing(Page::getLoadTime).reversed())
            .collect(Collectors.toList());
        for (var i = 0; i < sorted.size(); i++) {
            var page = sorted.get(i);
            var rank = ((double) (i + 1) / sorted.size());
            page.setMetaRank(rank);
        }

        log.debug("{} pages ranked by meta for index {}",
            index.getPages().size(), index.getTopic());
        next(index);
    }
}
