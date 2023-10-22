package com.osearch.crawler.fixture;

import java.util.List;
import java.util.stream.Collectors;

public class CrawlerControllerFixture {
    public static final String START_URL = "/api/crawler/start";
    public static final String STOP_URL = "/api/crawler/stop";
    public static final String IS_RUNNING_URL = "/api/crawler/running";

    public static final List<String> INITIAL_URLS =
        List.of(
            "https://www.youtube.com",
            "https://www.baeldung.com/"
        );


    public static String startRequestJson() {
        var urls = INITIAL_URLS.stream()
            .map(str -> "\"" + str + "\"")
            .collect(Collectors.toList());
        var urlsJson = String.join(", ", urls);
        return "{ \"urls\": ["  + urlsJson + "]}";
    }
}
