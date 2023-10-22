package com.osearch.ranker.domain.entity;

import com.osearch.ranker.domain.valueobject.Significance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a topic mentioned on a page.
 */
@Data
@Builder
@AllArgsConstructor
public class Topic implements Comparable<Topic> {
    private String name;
    private Significance significance;

    @Override
    public int compareTo(Topic other) {
        return significance.compareTo(other.significance);
    }
}
