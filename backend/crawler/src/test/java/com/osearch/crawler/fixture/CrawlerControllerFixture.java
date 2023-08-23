package com.osearch.crawler.fixture;

import java.util.List;
import java.util.stream.Collectors;

public class CrawlerControllerFixture {
    public static final String START_URL = "/crawler/start";
    public static final String STOP_URL = "/crawler/stop";
    public static final String IS_RUNNING_URL = "/crawler/running";

    public static List<String> initialUrls() {
        return List.of(
            "https://www.youtube.com",
            "https://www.baeldung.com/"
        );
    }

    public static String startRequestJson() {
        var urls = initialUrls().stream()
            .map(str -> "\"" + str + "\"")
            .collect(Collectors.toList());
        var urlsJson = String.join(", ", urls);
        return "{ \"urls\": ["  + urlsJson + "]}";
    }
}
