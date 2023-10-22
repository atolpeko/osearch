package com.osearch.ranker.fixture;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.time.Duration;
import java.util.Set;

public class FinalRankerFixture {
    public static final double TOPIC_RANK_WEIGHT = 0.5;
    public static final double PAGE_RANK_WEIGHT = 0.3;
    public static final double META_RANK_WEIGHT = 0.2;

    public static final double PAGE_1_TOPIC_RANK = 1.0;
    public static final double PAGE_2_TOPIC_RANK = 0.75;
    public static final double PAGE_3_TOPIC_RANK = 0.5;
    public static final double PAGE_4_TOPIC_RANK = 0.25;

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
        .topicRank(PAGE_1_TOPIC_RANK)
        .pageRank(PAGE_1_PAGE_RANK)
        .metaRank(PAGE_1_META_RANK)
        .build();

    public static final Page PAGE_2 = Page.builder()
        .topicRank(PAGE_2_TOPIC_RANK)
        .pageRank(PAGE_2_PAGE_RANK)
        .metaRank(PAGE_2_META_RANK)
        .build();

    public static final Page PAGE_3 = Page.builder()
        .topicRank(PAGE_3_TOPIC_RANK)
        .pageRank(PAGE_3_PAGE_RANK)
        .metaRank(PAGE_3_META_RANK)
        .build();

    public static final Page PAGE_4 = Page.builder()
        .topicRank(PAGE_4_TOPIC_RANK)
        .pageRank(PAGE_4_PAGE_RANK)
        .metaRank(PAGE_4_META_RANK)
        .build();

    public static final Index INDEX = Index.builder()
        .topic("A")
        .pages(Set.of(PAGE_4, PAGE_3, PAGE_2, PAGE_1))
        .build();
}
