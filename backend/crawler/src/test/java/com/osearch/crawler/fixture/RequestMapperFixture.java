package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapperFixture {

    public static String startRequestJson() {
        var urls = initialUrls().stream()
            .map(str -> "\"" + str + "\"")
            .collect(Collectors.toList());
        var urlsJson = String.join(", ", urls);
        return "{ \"operation\":\"START\" \"urls\": ["  + urlsJson + "]}";
    }

    public static Request startRequest() {
        return Request.builder()
            .operation(Operation.START)
            .urls(initialUrls())
            .build();
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

    public static Request stopRequest() {
        return Request.builder()
            .operation(Operation.STOP)
            .build();
    }

    public static String invalidStartRequestJson() {
        return "{ \"operation\":\"START\" }";
    }

    public static Request invalidStartRequest() {
        return Request.builder()
            .operation(Operation.START)
            .build();
    }

    public static String invalidRequestJson() {
        return "{  }";
    }

    public static Request invalidStopRequest() {
        return Request.builder().build();
    }
}
