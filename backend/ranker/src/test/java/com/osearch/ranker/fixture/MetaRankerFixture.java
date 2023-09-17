package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.Set;

public class MetaRankerFixture {
    public static final double PAGE_1_RANK = 1.0;
    public static final double PAGE_2_RANK = 0.75;
    public static final double PAGE_3_RANK = 0.5;
    public static final double PAGE_4_RANK = 0.25;

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
