package com.osearch.indexer.fixture;

import com.osearch.indexer.domain.entity.IndexRequest;
import com.osearch.indexer.domain.entity.Page;
import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.valueobject.Significance;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IndexServiceFixture {
    public static final List<Locale> SUPPORTED_LOCALES = Stream.of("en", "ar")
        .map(Locale::new)
        .collect(Collectors.toList());

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

    public static final String CONTENT_UNSUPPORTED_LOCALE =
        "<!doctype html>\n" +
            "<html lang=\"ru\">\n " +
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

    public static final IndexRequest REQUEST =
        IndexRequest.builder()
            .url(URL)
            .content(CONTENT)
            .loadTime(LOAD_TIME)
            .build();

    public static final IndexRequest REQUEST_UNSUPPORTED_LOCALE =
        IndexRequest.builder()
            .url(URL)
            .content(CONTENT_UNSUPPORTED_LOCALE)
            .loadTime(LOAD_TIME)
            .build();

    public static final Set<Topic> EXTRACTED_TOPICS = Set.of(
        Topic.builder()
            .mainSubject("Blog")
            .significance(Significance.of(40))
            .build()
    );

    public static Set<Topic> allTopics() {
        var title = Topic.builder()
            .mainSubject("My Travel Blog")
            .significance(Significance.of(100))
            .build();

        var topics = new HashSet<>(EXTRACTED_TOPICS);
        topics.add(title);
        return topics;
    }

    public static final Page PAGE =
        Page.builder()
            .url(URL)
            .title("My Travel Blog")
            .topics(new HashSet<>(allTopics()))
            .loadTime(Duration.ofMillis(LOAD_TIME))
            .build();
}
