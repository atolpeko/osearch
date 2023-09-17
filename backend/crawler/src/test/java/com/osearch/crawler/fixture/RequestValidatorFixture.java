package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;

import java.util.List;

public class RequestValidatorFixture {

    public static Request validStartRequest() {
        return Request.builder()
            .operation(Operation.START)
            .urls(List.of("URL"))
            .build();
    }

    public static Request validStopRequest() {
        return Request.builder()
            .operation(Operation.STOP)
            .build();
    }

    public static Request invalidStartRequest() {
        return Request.builder()
            .operation(Operation.START)
            .urls(List.of())
            .build();
    }

    public static Request invalidStopRequest() {
        return Request.builder().build();
    }
}
