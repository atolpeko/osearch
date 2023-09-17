package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Keyword;
import com.osearch.ranker.domain.entity.Page;

import java.util.Set;

public class KeywordRankerFixture {
    public static final double PAGE_1_RANK = 1.0;
    public static final double PAGE_2_RANK = 0.75;
    public static final double PAGE_3_RANK = 0.5;
    public static final double PAGE_4_RANK = 0.25;

    public static final Page PAGE_1 = Page.builder()
        .keywords(Set.of(
            new Keyword("Java", 10)
        ))
        .build();

    public static final Page PAGE_2 = Page.builder()
        .keywords(Set.of(
            new Keyword("Java", 5)
        ))
        .build();

    public static final Page PAGE_3 = Page.builder()
        .keywords(Set.of(
            new Keyword("Java", 3)
        ))
        .build();

    public static final Page PAGE_4 = Page.builder()
        .keywords(Set.of(
            new Keyword("Java", 1)
        ))
        .build();

    public static final Index INDEX = Index.builder()
        .keywords("Java")
        .pages(Set.of(PAGE_3, PAGE_4, PAGE_2, PAGE_1))
        .build();
}
