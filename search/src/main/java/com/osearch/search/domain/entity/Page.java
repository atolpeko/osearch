package com.osearch.search.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a web page with its URL, title, and rank.
 */
@Data
@Builder
@AllArgsConstructor
public class Page {
    private String url;
    private String title;
}
