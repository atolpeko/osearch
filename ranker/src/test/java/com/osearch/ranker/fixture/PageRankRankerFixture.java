package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.util.Set;

public class PageRankRankerFixture {
    public static final int ITERATIONS = 20;
    public static final double DAMPING_FACTOR = 0.85;

    public static final Page PAGE_1 = Page.builder()
        .url("1")
        .referredPages(Set.of(
            Page.builder().pageRank(0.50).build(),
            Page.builder().pageRank(0.40).build()
        ))
        .build();

    public static final Page PAGE_2 = Page.builder()
        .url("2")
        .referredPages(Set.of(
            Page.builder().pageRank(0.25).build(),
            Page.builder().pageRank(0.23).build()
        ))
        .build();

    public static final Page PAGE_3 = Page.builder()
        .url("3")
        .referredPages(Set.of(
            Page.builder().pageRank(0.15).build(),
            Page.builder().pageRank(0.10).build()
        ))
        .build();

    public static final Page PAGE_4 = Page.builder()
        .url("4")
        .referredPages(Set.of(
            Page.builder().pageRank(0.1).build()
        ))
        .build();

    public static final Index INDEX = Index.builder()
        .topic("Java")
        .pages(Set.of(PAGE_4, PAGE_3, PAGE_2, PAGE_1))
        .build();
}
