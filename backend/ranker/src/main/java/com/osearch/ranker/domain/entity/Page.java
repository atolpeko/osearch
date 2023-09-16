package com.osearch.ranker.domain.entity;

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
    private Set<Page> referredPages;
    private Set<Keyword> keywords;
    private boolean isIndexed;

    private double totalRank;
    private double pageRank;
    private double keywordRank;
    private double metaRank;
}
