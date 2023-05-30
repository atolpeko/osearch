package com.osearch.indexer.service.entity;

import lombok.Data;

@Data
public class Keyword {
    private final String value;
    private final long occurrences;
}
