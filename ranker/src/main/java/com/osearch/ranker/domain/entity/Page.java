package com.osearch.ranker.domain.entity;

import java.time.Duration;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The Page class represents a web page.
 */
@Data
@Builder
@AllArgsConstructor
public class Page {
    private Long sourceId;
    private String url;
    private String title;
    private Duration loadTime;
    private Set<Page> referredPages;
    private Set<Topic> topics;
    private boolean isIndexed;

    private double totalRank;
    private double pageRank;
    private double topicRank;
    private double metaRank;
}
