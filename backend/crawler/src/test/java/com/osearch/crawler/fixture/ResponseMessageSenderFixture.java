package com.osearch.crawler.fixture;

import com.osearch.crawler.inout.messaging.entity.Response;
import com.osearch.crawler.inout.messaging.entity.Response.Status;

import java.time.LocalDateTime;

public class ResponseMessageSenderFixture {
    public static final String TOPIC = "TOPIC";
    public static final Status RESPONSE_STATUS = Status.SUCCESSFUL;
    public static final String RESPONSE_DESCRIPTION = "DESCRIPTION";
    public static final LocalDateTime REQUEST_TIME = LocalDateTime.now();

    public static Response response() {
        return Response.builder()
                .status(RESPONSE_STATUS)
                .description(RESPONSE_DESCRIPTION)
                .requestDateTime(REQUEST_TIME)
                .build();
    }

    public static String responseJson() {
        return "{ status: " + RESPONSE_STATUS
                + ", description: " + RESPONSE_DESCRIPTION
                + ", requestDateTime: " + REQUEST_TIME
                + "}";
    }
}