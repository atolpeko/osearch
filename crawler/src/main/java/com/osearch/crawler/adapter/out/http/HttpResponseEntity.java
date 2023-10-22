package com.osearch.crawler.adapter.out.http;

import com.osearch.crawler.application.port.entity.HttpResponse;

import java.time.Duration;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "loadTime")
public class HttpResponseEntity implements HttpResponse {
    private final String url;
    private final String content;
    private final Duration loadTime;
}
