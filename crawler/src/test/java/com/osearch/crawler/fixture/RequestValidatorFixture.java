package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;

import java.util.List;

public class RequestValidatorFixture {
    public static final String VALID_URL = "https://www.datacamp.com/";

    public static final Request VALID_START_REQUEST =
        Request.builder()
            .operation(Operation.START)
            .urls(List.of(VALID_URL))
            .build();

    public static final Request VALID_STOP_REQUEST =
        Request.builder()
            .operation(Operation.STOP)
            .build();

    public static final Request START_REQUEST_NO_URLS =
        Request.builder()
            .operation(Operation.START)
            .urls(List.of())
            .build();

    public static final Request START_REQUEST_BLANK_URLS =
        Request.builder()
            .operation(Operation.START)
            .urls(List.of(VALID_URL, ""))
            .build();

    public static final Request START_REQUEST_INVALID_URLS =
        Request.builder()
            .operation(Operation.START)
            .urls(List.of(VALID_URL, "htts://www.datacamp.com/"))
            .build();

    public static final Request INVALID_STOP_REQUEST = Request.builder().build();
}
