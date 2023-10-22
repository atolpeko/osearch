package com.osearch.search.fixture;

import com.osearch.search.domain.entity.Page;
import java.util.List;

public class PageRepositoryFixture {
    public static final int OFFSET = 0;
    public static final int LIMIT = 10;

    public static final String TOPIC_1 = "Work";
    public static final String TOPIC_2 = "Code";

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

    public static final List<Page> TOPIC_1_PAGES = List.of(PAGE_1, PAGE_2);
    public static final List<Page> TOPIC_2_PAGES = List.of(PAGE_3, PAGE_4);
}
