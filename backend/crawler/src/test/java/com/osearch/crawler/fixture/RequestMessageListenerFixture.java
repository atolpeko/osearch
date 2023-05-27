package com.osearch.crawler.fixture;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMessageListenerFixture {

    public static String startRequestJson() {
        var urls = initialUrls().stream()
                .map(str -> "\"" + str + "\"")
                .collect(Collectors.toList());
        var urlsJson = String.join(", ", urls);
        return "{ \"operation\":\"START\" \"urls\": ["  + urlsJson + "]}";
    }

    public static List<String> initialUrls() {
        return List.of(
                "https://www.youtube.com",
                "https://www.baeldung.com/"
        );
    }

    public static String stopRequestJson() {
        return "{ \"operation\":\"STOP\" }";
    }

    public static String invalidStartRequest() {
        return "{ \"operation\":\"START\" }";
    }

    public static String invalidRequest() {
        return "{  }";
    }
}
