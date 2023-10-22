package com.osearch.search.domain.entity;

import lombok.Builder;
import lombok.Data;

/**
 * This class represents information about an index.
 */
@Data
@Builder
public class IndexInfo {
    private String index;
    private int pagesCount;
}
