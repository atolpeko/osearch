package com.osearch.indexer.fixture;

import com.osearch.indexer.domain.entity.IndexRequest;
import com.osearch.indexer.domain.entity.Page;
import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.valueobject.Significance;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class IndexUseCaseFixture {
    public static final long ID = 1L;
    public static final String URL = "https://host/resource";
    public static final long LOAD_TIME = 1000L;
    public static final String CONTENT =
        "<!doctype html>\n" +
            "<html lang=\"en\">\n " +
            "<head> " +
                "<meta charset=\"UTF-8'\"> " +
                "<meta name=\"author\" content=\"John Doe\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>My Travel Blog</title>" +
            "</head>\n " +
            "<body>\n " +
                "<h1>Hello! This is a travel blog. Today I'm gonna show you Spain</h1>\n " +
                "<p>Holiday vibes are a way of life in beautiful Spain!</p> " +
            "</body>\n " +
        "</html>";

    public static final String TITLE = "TITLE";
    public static final String NESTED_URL_1 = "http://host/resource1";
    public static final String NESTED_URL_2 = "http://host/resource2";

    public static final IndexRequest REQUEST =
        IndexRequest.builder()
            .url(URL)
            .content(CONTENT)
            .loadTime(LOAD_TIME)
            .nestedUrls(Set.of(NESTED_URL_1, NESTED_URL_2))
            .build();

    public static final Set<Topic> TOPICS = Set.of(
        Topic.builder()
            .mainSubject("Blog")
            .significance(Significance.of(40))
            .build(),

        Topic.builder()
            .mainSubject("My Travel Blog")
            .significance(Significance.of(100))
            .build()
    );

    public static final Page PAGE =
        Page.builder()
            .url(URL)
            .title(TITLE)
            .loadTime(Duration.ofMillis(LOAD_TIME))
            .topics(new HashSet<>(TOPICS))
            .nestedUrls(Set.of(NESTED_URL_1, NESTED_URL_2))
            .build();
}
