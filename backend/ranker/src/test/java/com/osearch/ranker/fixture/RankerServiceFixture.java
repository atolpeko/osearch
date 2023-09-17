package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.Set;

public class RankerServiceFixture {
    public static final double KEYWORD_RANK_WEIGHT = 0.5;
    public static final double PAGE_RANK_WEIGHT = 0.3;
    public static final double META_RANK_WEIGHT = 0.2;

    public static final double PAGE_1_KEYWORD_RANK = 1.0;
    public static final double PAGE_2_KEYWORD_RANK = 0.75;
    public static final double PAGE_3_KEYWORD_RANK = 0.5;
    public static final double PAGE_4_KEYWORD_RANK = 0.25;

    public static final double PAGE_1_PAGE_RANK = 0.4;
    public static final double PAGE_2_PAGE_RANK = 0.3;
    public static final double PAGE_3_PAGE_RANK = 0.2;
    public static final double PAGE_4_PAGE_RANK = 0.1;

    public static final double PAGE_1_META_RANK = 1.0;
    public static final double PAGE_2_META_RANK = 0.75;
    public static final double PAGE_3_META_RANK = 0.5;
    public static final double PAGE_4_META_RANK = 0.25;

    public static final double PAGE_1_TOTAL_RANK = 0.8200000000000001;
    public static final double PAGE_2_TOTAL_RANK = 0.615;
    public static final double PAGE_3_TOTAL_RANK = 0.41000000000000003;
    public static final double PAGE_4_TOTAL_RANK = 0.20500000000000002;

    public static final Page PAGE_1 = Page.builder()
        .loadTime(Duration.ofMillis(10))
        .build();

    public static final Page PAGE_2 = Page.builder()
        .loadTime(Duration.ofMillis(100))
        .build();

    public static final Page PAGE_3 = Page.builder()
        .loadTime(Duration.ofMillis(1000))
        .build();

    public static final Page PAGE_4 = Page.builder()
        .loadTime(Duration.ofMillis(10000))
        .build();

    public static final Index INDEX = Index.builder()
        .keywords("A")
        .pages(Set.of(PAGE_4, PAGE_3, PAGE_2, PAGE_1))
        .build();
}
