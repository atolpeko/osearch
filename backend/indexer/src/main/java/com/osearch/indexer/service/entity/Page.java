package com.osearch.indexer.service.entity;

import java.time.Duration;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "refersTo")
@ToString(exclude = "refersTo")
public class Page {
    private String url;
    private String title;
    private Duration loadTime;
    private boolean isIndexed;
    private Set<Page> refersTo;
    private Set<Keyword> keywords;
}
