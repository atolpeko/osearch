package com.osearch.crawler.adapter.out.messaging.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class PageDto {
    private final String url;
    private final String content;
    private final Long loadTime;
    private final List<String> nestedUrls;

    @Override
    public String toString() {
        return "PageDto{" +
            "url='" + url + '\'' +
            ", content=HTML" +
            ", loadTime=" + loadTime +
            ", nestedUrlsSize=" + nestedUrls.size() +
            '}';
    }
}
