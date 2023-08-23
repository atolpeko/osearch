package com.osearch.indexer.domain.entity;

import java.time.Duration;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Page {
    private String url;
    private String title;
    private Duration loadTime;
    private Set<String> nestedUrls;
    private Set<Keyword> keywords;
}
