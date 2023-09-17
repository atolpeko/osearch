package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Keyword;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

public class RankerUseCaseFixture {
    public static final long PAGE_ID = 4343L;
    public static final double NEW_PAGE_TOTAL_RANK = 0.75;

    public static final Set<Keyword> KEYWORDS = Set.of(
        new Keyword("Java", 5),
        new Keyword("Programming", 5),
        new Keyword("Language", 1),
        new Keyword("C++", 2)
    );

    public static final Set<Page> REFERRED = Set.of(
        Page.builder().build()
    );

    public static final Page PAGE = Page.builder()
        .url("URL")
        .title("TITLE")
        .keywords(KEYWORDS)
        .referredPages(REFERRED)
        .loadTime(Duration.ZERO)
        .isIndexed(true)
        .build();

    public static final Set<String> INDEXED_KEYWORDS = Set.of(
        "TITLE", "Java Programming", "Java", "Programming", "C++"
    );

    public static Set<Index> newIndexes() {
        return INDEXED_KEYWORDS.stream()
            .map(key -> new Index(key, Set.of(PAGE)))
            .collect(Collectors.toSet());
    }
}
