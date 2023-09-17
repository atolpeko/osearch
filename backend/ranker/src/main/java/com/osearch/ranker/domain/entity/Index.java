package com.osearch.ranker.domain.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Index {
    private String keywords;
    private Set<Page> pages;

    public void addPage(Page page) {
        pages.add(page);
    }
}
