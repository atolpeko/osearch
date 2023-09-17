package com.osearch.ranker.domain.service.ranker.impl;

import static com.osearch.ranker.util.DurationLogger.withDurationLog;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.util.Comparator;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

@Log4j2
@RequiredArgsConstructor
public class MetaRanker implements Ranker {

    @Override
    public void rank(Index index) {
        var pages = index.getPages();
        log.debug("Ranking {} pages by meta", pages.size());
        Runnable task = () -> {
            var sorted = pages.stream()
                .sorted(Comparator.comparing(Page::getLoadTime).reversed())
                .collect(Collectors.toList());
            for (var i = 0; i < sorted.size(); i++) {
                var page = sorted.get(i);
                var rank = ((double) (i + 1) / sorted.size());
                page.setMetaRank(rank);
            }
        };

        var message = pages.size() + " pages ranked by meta";
        withDurationLog(task, message, Level.DEBUG);
    }
}
