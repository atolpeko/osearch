package com.osearch.indexer.inout.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("HTMLElement")
@Data
@Builder
@AllArgsConstructor
public class HTMLElementDto {

    @Id
    @GeneratedValue
    private Long id;

    private String key;
    private String value;
}
