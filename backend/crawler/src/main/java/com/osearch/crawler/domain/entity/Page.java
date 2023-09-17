package com.osearch.crawler.domain.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"foundAt"})
public class Page {
    private String content;
    private String hash;
    private String url;
    private String urlHash;
    private Duration loadTime;
    private LocalDateTime foundAt;
    private List<String> nestedUrls;
}
