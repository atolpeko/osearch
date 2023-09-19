package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.in.messaging.entity.Response;
import com.osearch.crawler.adapter.in.messaging.entity.Response.Status;

import java.time.LocalDateTime;

public class ResponseMessageSenderFixture {
    public static final String TOPIC = "TOPIC";
    public static final Status RESPONSE_STATUS = Status.SUCCESSFUL;
    public static final String RESPONSE_DESCRIPTION = "DESCRIPTION";
    public static final LocalDateTime REQUEST_TIME = LocalDateTime.now();

    public static final Response RESPONSE =
        Response.builder()
            .status(RESPONSE_STATUS)
            .description(RESPONSE_DESCRIPTION)
            .requestDateTime(REQUEST_TIME)
            .build();

    public static final String RESPONSE_JSON =
        "{ status: " + RESPONSE_STATUS
            + ", description: " + RESPONSE_DESCRIPTION
            + ", requestDateTime: " + REQUEST_TIME
            + "}";
}
