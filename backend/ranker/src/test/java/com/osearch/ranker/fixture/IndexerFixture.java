package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Keyword;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.Set;

public class IndexerFixture {
    public static final String URL = "";
    public static final String TITLE = "Java Programming";
    public static final Duration LOAD_TIME = Duration.ZERO;
    public static final boolean IS_INDEXED = true;

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
        .url(URL)
        .title(TITLE)
        .keywords(KEYWORDS)
        .referredPages(REFERRED)
        .loadTime(LOAD_TIME)
        .isIndexed(IS_INDEXED)
        .build();

    public static Set<String> RESULT = Set.of(
        "Java Programming", "Java", "Programming", "C++"
    );
}
