package com.osearch.ranker.domain.service.ranker.impl;

import static com.osearch.ranker.util.DurationLogger.withDurationLog;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Keyword;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

@Log4j2
public class KeywordRanker implements Ranker {

    @Override
    public void rank(Index index) {
        var pages = index.getPages();
        Runnable task = () -> {
            log.debug("Ranking {} pages for keywords {}", pages.size(), index.getKeywords());
            var sorted = pages.stream()
                .sorted(Comparator.comparing(page ->
                    countOccurrences(page.getKeywords(), index.getKeywords())))
                .collect(Collectors.toList());
            for (var i = 0; i < sorted.size(); i++) {
                var page = sorted.get(i);
                var rank = ((double) (i + 1) / sorted.size());
                page.setKeywordRank(rank);
            }
        };

        var message = pages.size() + " pages for keywords " + index.getKeywords() + " ranked";
        withDurationLog(task, message, Level.DEBUG);
    }

    private long countOccurrences(Set<Keyword> keywords, String keywordsStr) {
        return  keywords.stream()
            .filter(keyword -> keyword.getValue().equals(keywordsStr))
            .findFirst()
            .map(Keyword::getOccurrences)
            .orElse(0L);
    }
}
