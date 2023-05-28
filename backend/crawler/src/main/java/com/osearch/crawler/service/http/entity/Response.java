package com.osearch.crawler.service.http.entity;

import java.time.Duration;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "loadTime")
public class Response {
    private final String url;
    private final String content;
    private final Duration loadTime;
}
