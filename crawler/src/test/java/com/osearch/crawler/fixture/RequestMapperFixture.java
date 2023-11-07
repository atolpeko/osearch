package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapperFixture {

    public static String startRequestJson() {
        var urls = INITIAL_URLS.stream()
            .map(str -> "\"" + str + "\"")
            .collect(Collectors.toList());
        var urlsJson = String.join(", ", urls);
        return "{ \"operation\":\"START\" \"urls\": ["  + urlsJson + "]}";
    }

    public static final List<String> INITIAL_URLS =
        List.of(
            "https://www.youtube.com",
            "https://www.baeldung.com/"
        );

    public static final Request START_REQUEST =
        Request.builder()
            .operation(Operation.START)
            .urls(INITIAL_URLS)
            .build();

    public static final Request STOP_REQUEST =
        Request.builder()
            .operation(Operation.STOP)
            .build();

    public static final Request INVALID_START_REQUEST =
        Request.builder()
            .operation(Operation.START)
            .build();

    public static final Request INVALID_STOP_REQUEST = Request.builder().build();
    public static final String STOP_REQUEST_JSON = "{ \"operation\":\"STOP\" }";
    public static final String INVALID_START_REQUEST_JSON = "{ \"operation\":\"START\" }";
    public static final String INVALID_STOP_REQUEST_JSON = "{  }";
}
