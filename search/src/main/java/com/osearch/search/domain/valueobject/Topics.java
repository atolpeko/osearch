package com.osearch.search.domain.valueobject;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a topic along with its associated side topics.
 */
@Data
@Builder
public class Topics {
    private String mainTopic;
    private List<String> sideTopics;
}
