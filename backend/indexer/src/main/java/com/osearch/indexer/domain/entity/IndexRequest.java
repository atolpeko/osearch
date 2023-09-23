package com.osearch.indexer.domain.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request to index a webpage.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndexRequest {
    private String url;
    private String content;
    private Long loadTime;
    private Set<String> nestedUrls;
}
