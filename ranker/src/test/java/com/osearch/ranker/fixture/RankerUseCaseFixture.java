package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.domain.entity.Topic;
import com.osearch.ranker.domain.valueobject.Significance;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

public class RankerUseCaseFixture {
    public static final long PAGE_ID = 4343L;
    public static final double NEW_PAGE_TOTAL_RANK = 0.75;

    public static final Set<Topic> TOPICS = Set.of(
        new Topic("Java", Significance.of(5)),
        new Topic("Programming", Significance.of(5)),
        new Topic("Language", Significance.of(1)),
        new Topic("C++", Significance.of(2))
    );

    public static final Set<Page> REFERRED = Set.of(
        Page.builder().build()
    );

    public static final Page PAGE = Page.builder()
        .sourceId(111L)
        .url("URL")
        .title("TITLE")
        .topics(TOPICS)
        .referredPages(REFERRED)
        .loadTime(Duration.ZERO)
        .isIndexed(true)
        .build();

    public static final Set<String> TOPIC_NAMES = TOPICS.stream()
        .map(Topic::getName)
        .collect(Collectors.toSet());

    public static Set<Index> newIndexes() {
        return TOPIC_NAMES.stream()
            .map(key -> new Index(key, Set.of(PAGE)))
            .collect(Collectors.toSet());
    }
}
