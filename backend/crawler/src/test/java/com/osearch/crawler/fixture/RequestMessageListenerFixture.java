package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;
import com.osearch.crawler.adapter.in.messaging.entity.Response;
import com.osearch.crawler.adapter.in.messaging.entity.Response.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RequestMessageListenerFixture {
    public static final String TOPIC = "TOPIC";

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

    public static Request startRequest() {
        return Request.builder()
            .operation(Operation.START)
            .urls(initialUrls())
            .build();
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

    public static String invalidRequestJson() {
        return "{  }";
    }

    public static Response successResponseJson() {
        return Response.builder()
            .status(Status.SUCCESSFUL)
            .requestDateTime(LocalDateTime.now())
            .build();
    }

    public static Response invalidStartRequestResponse() {
        return Response.builder()
            .status(Status.ERROR)
            .description("{ \"operation\":\"START\" } is not a valid request")
            .requestDateTime(LocalDateTime.now())
            .build();
    }

    public static Response invalidStopRequestResponse() {
        return Response.builder()
            .status(Status.ERROR)
            .description("{  } is not a valid request")
            .requestDateTime(LocalDateTime.now())
            .build();
    }

    public static Response crawlerAlreadyRunningResponse() {
        return Response.builder()
            .status(Status.ERROR)
            .description("Crawler is already running")
            .requestDateTime(LocalDateTime.now())
            .build();
    }

    public static Response crawlerNotRunningResponse() {
        return Response.builder()
            .status(Status.ERROR)
            .description("Crawler is not running")
            .requestDateTime(LocalDateTime.now())
            .build();
    }
}
