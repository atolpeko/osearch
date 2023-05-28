package com.osearch.crawler.fixture;

import com.osearch.crawler.inout.messaging.entity.URLDto;

import java.util.List;
import java.util.stream.Collectors;

public class UrlMessageSenderFixture {
    public static final String URL = "https://stackoverflow.com/questions/1.html";
    public static final String TOPIC = "TOPIC";
    public static final String CONTENT =
            "<!doctype html>\n" +
                "<html>\n " +
                    "<head></head>\n " +
                    "<body>\n " +
                        "<h1>Hello</h1>\n " +
                    "</body>\n " +
                "</html>";
    public static final Long LOAD_TIME = 1000L;
    public static final List<String> NESTED_URLS = List.of(
            "http://stackoverflow.com/questions/2.htm",
            "https://stackoverflow.com/questions/3.jps"
    );

    public static URLDto url() {
        return new URLDto(URL, CONTENT, LOAD_TIME, NESTED_URLS);
    }

    public static String urlJson() {
        var url = url();
        var nested = url.getNestedUrls().stream()
                .map(str -> "{ \"value\": \"" + str + "\" }")
                .collect(Collectors.toList());
        var urlsJson = String.join(", ", nested);

        return "{ \"value\": \"" + url.getUrl() + "\","
                + "\"content\": " + url.getContent() + "\""
                + "\"loadTime\": " + url.getLoadTime() + "\""
                + "\"nestedUrls\": [" + nested + "]}";
    }
}
