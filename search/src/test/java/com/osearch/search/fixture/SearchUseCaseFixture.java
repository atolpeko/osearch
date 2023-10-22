package com.osearch.search.fixture;

import com.osearch.search.application.entity.Pageable;
import com.osearch.search.domain.entity.Page;
import com.osearch.search.domain.valueobject.Topics;

import java.util.Collections;
import java.util.List;

public class SearchUseCaseFixture {
    public static final int OFFSET_FOR_MAIN_TOPIC = 0;
    public static final int OFFSET_FOR_MAIN_WITH_SIDE = 4;
    public static final int OFFSET_FOR_SIDE_TOPIC = 0;

    public static final int LIMIT = 3;
    public static final int LIMIT_PLUS_1 = 4;

    public static final String SEARCH_STRING = "TOPIC";
    public static final String MAIN_TOPIC = SEARCH_STRING;
    public static final String SIDE_TOPIC_1 = "SIDE_TOPIC_1";

    public static final Page PAGE_1 = Page.builder()
        .title("LinkedIn")
        .url("https://www.linkedin.com")
        .build();

    public static final Page PAGE_2 = Page.builder()
        .title("Glassdoor")
        .url("https://www.glassdoor.es/")
        .build();

    public static final Page PAGE_3 = Page.builder()
        .title("GitHub")
        .url("https://github.com/")
        .build();

    public static final Page PAGE_4 = Page.builder()
        .title("Stack Overflow")
        .url("https://stackoverflow.com/")
        .build();

    public static final Page PAGE_5 = Page.builder()
        .title("Some other")
        .url("https://stackoverflow.com/")
        .build();

    public static final List<Page> MAIN_PAGES = List.of(PAGE_1, PAGE_2, PAGE_3);

    public static final List<Page> MAIN_PAGES_PLUS_1 =
        List.of(PAGE_1, PAGE_2, PAGE_3, PAGE_4);

    public static final Pageable<Page> MAIN_PAGEABLE =
        Pageable.of(MAIN_PAGES, true);

    public static final List<Page> LAST_MAIN_PAGE = List.of(PAGE_4);

    public static final List<Page> SIDE_PAGES = List.of(PAGE_5);

    public static final Pageable<Page> PAGEABLE_WITH_SIDE =
        Pageable.of(List.of(PAGE_4, PAGE_5), false);

    public static final Topics MAIN_TOPICS =
        Topics.builder()
            .mainTopic(MAIN_TOPIC)
            .sideTopics(Collections.emptyList())
            .build();

    public static final Topics TOPICS_WITH_SIDE =
        Topics.builder()
            .mainTopic(SEARCH_STRING)
            .sideTopics(List.of(SIDE_TOPIC_1))
            .build();
}
