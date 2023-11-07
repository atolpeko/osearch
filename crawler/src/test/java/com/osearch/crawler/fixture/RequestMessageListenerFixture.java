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
        return "{ \"operation\":\"START\" \"urls\": ["  + urlsJson + "]}";
    }

    public static final Request START_REQUEST =
        Request.builder()
            .operation(Operation.START)
            .urls(INITIAL_URLS)
            .build();

    public static final Request STOP_REQUEST =
        Request.builder()
            .operation(Operation.STOP)
            .build();

    public static final Response SUCCESSFUL_RESPONSE =
        Response.builder()
            .status(Status.SUCCESSFUL)
            .requestDateTime(LocalDateTime.now())
            .build();


    public static final Response INVALID_START_RESPONSE =
        Response.builder()
            .status(Status.ERROR)
            .description("{ \"operation\":\"START\" } INVALID_START")
            .requestDateTime(LocalDateTime.now())
            .build();

    public static final Response INVALID_STOP_RESPONSE =
        Response.builder()
            .status(Status.ERROR)
            .description("{  } INVALID_STOP")
            .requestDateTime(LocalDateTime.now())
            .build();

    public static final Response CRAWLER_ALREADY_RINNING_RESPONSE =
        Response.builder()
            .status(Status.ERROR)
            .description("Crawler is already running")
            .requestDateTime(LocalDateTime.now())
            .build();

    public static final Response CRAWLER_NOT_RINNING_RESPONSE =
        Response.builder()
            .status(Status.ERROR)
            .description("Crawler is not running")
            .requestDateTime(LocalDateTime.now())
            .build();

    public static final String STOP_REQUEST_JSON = "{ \"operation\":\"STOP\" }";
    public static final String INVALID_START_REQUEST_JSON = "{ \"operation\":\"START\" }";
    public static final String INVALID_STOP_REQUEST_JSON = "{  }";
}
