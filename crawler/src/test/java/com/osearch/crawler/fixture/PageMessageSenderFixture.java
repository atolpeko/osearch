package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.out.messaging.entity.PageDto;
import com.osearch.crawler.domain.entity.Page;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class PageMessageSenderFixture {
    public static final String URL = "https://stackoverflow.com/questions/1.html";
    public static final String URL_HASH = "dfsfdsf3t34t34";
    public static final String TOPIC = "TOPIC";
    public static final String CONTENT =
            "<!doctype html>\n" +
                "<html>\n " +
                    "<head></head>\n " +
                    "<body>\n " +
                        "<h1>Hello</h1>\n " +
                    "</body>\n " +
                "</html>";
    public static final String CONTENT_HASH = "dfsfdsf3t34t34";
    public static final Long LOAD_TIME = 1000L;
    public static final List<String> NESTED_URLS = List.of(
        "http://stackoverflow.com/questions/2.htm",
        "https://stackoverflow.com/questions/3.jps"
    );

    public static final Page PAGE =
        Page.builder()
            .url(URL)
            .content(CONTENT)
            .hash(CONTENT_HASH)
            .urlHash(URL_HASH)
            .nestedUrls(NESTED_URLS)
            .loadTime(Duration.of(LOAD_TIME, ChronoUnit.MILLIS))
            .foundAt(LocalDateTime.now())
            .build();

    public static final PageDto PAGE_DTO =
        PageDto.builder()
            .url(URL)
            .content(CONTENT)
            .nestedUrls(NESTED_URLS)
            .loadTime(LOAD_TIME)
            .build();

    public static String pageJson() {
        var nested = PAGE.getNestedUrls().stream()
            .map(str -> "{ \"value\": \"" + str + "\" }")
            .collect(Collectors.toList());

        return "{ \"value\": \"" + PAGE.getUrl() + "\","
            + "\"content\": " + PAGE.getContent() + "\""
            + "\"loadTime\": " + PAGE.getLoadTime() + "\""
            + "\"nestedUrls\": [" + nested + "]}";
    }
}
