package com.osearch.indexer.domain.entity;

import java.time.Duration;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a web page.
 */
@Data
@Builder
@AllArgsConstructor
public class Page {
    private long id;
    private String url;
    private String title;
    private Duration loadTime;
    private Set<Topic> topics;
    private Set<String> nestedUrls;
}
