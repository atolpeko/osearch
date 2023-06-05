package com.osearch.indexer.service.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IndexRequest {
    private final String url;
    private final String content;
    private final Long loadTime;
    private final Set<String> nestedUrls;
}
