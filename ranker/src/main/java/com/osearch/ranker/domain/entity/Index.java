package com.osearch.ranker.domain.entity;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an index that stores topics and associated pages.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Index {
    private String topic;
    private Set<Page> pages;

    /**
     * Adds a page to the list of pages.
     *
     * @param page the page to be added to the list
     */
    public void addPage(Page page) {
        pages.add(page);
    }
}
