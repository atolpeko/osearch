package com.osearch.crawler.inout.messaging.entity;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class URLDto {
    private final String url;
    private final String content;
    private final Long loadTime;
    private final List<String> nestedUrls;

    @Override
    public String toString() {
        return "URLDto{" +
                "url='" + url + '\'' +
                ", content=HTML" +
                ", loadTime=" + loadTime +
                ", nestedUrlsSize=" + nestedUrls.size() +
                '}';
    }
}
