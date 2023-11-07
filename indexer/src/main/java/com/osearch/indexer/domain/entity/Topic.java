package com.osearch.indexer.domain.entity;

import com.osearch.indexer.domain.valueobject.Significance;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a topic mentioned on a page.
 */
@Data
@Builder
public class Topic implements Comparable<Topic> {
    private String mainSubject;
    private String mainAdjective;
    private String mainAction;
    private String mainActionTarget;
    private Significance significance;

    @Override
    public int compareTo(Topic other) {
        if (significance == null) {
            return 0;
        }

        return significance.compareTo(other.significance);
    }

    @Override
    public String toString() {
        var result = "";
        if (mainAdjective != null) {
            result += mainAdjective + " ";
        }
        result += mainSubject;
        if (mainAction != null) {
            result += " " + mainAction;
        }
        if (mainActionTarget != null) {
            result += " " + mainActionTarget;
        }

        return result.toLowerCase();
    }
}
