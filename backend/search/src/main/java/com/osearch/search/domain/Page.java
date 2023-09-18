package com.osearch.search.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Page {
    private String url;
    private double rank;
}
