package com.osearch.ranker.domain.ranker;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Topic;
import com.osearch.ranker.domain.valueobject.Significance;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

/**
 * Represents a TopicRanker, which ranks the pages in an index based on their
 * relevance to a specific topic.
 */
@Log4j2
public class TopicRanker extends BaseRanker {

    @Override
    public void rank(Index index) {
        var sorted = index.getPages().stream()
            .sorted(Comparator.comparing(page ->
                getSignificance(page.getTopics(), index.getTopic())))
            .collect(Collectors.toList());
        for (var i = 0; i < sorted.size(); i++) {
            var page = sorted.get(i);
            var rank = ((double) (i + 1) / sorted.size());
            page.setTopicRank(rank);
        }

        log.debug("{} pages ranked by topic for index {}",
            index.getPages().size(), index.getTopic());
        next(index);
    }

    private Significance getSignificance(Set<Topic> topics, String name) {
        return topics.stream()
            .filter(topic -> topic.getName().equals(name))
            .findFirst()
            .map(Topic::getSignificance)
            .orElse(Significance.of(0));
    }
}
