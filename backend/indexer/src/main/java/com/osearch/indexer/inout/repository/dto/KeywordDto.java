package com.osearch.indexer.inout.repository.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Keyword")
@Data
@Builder
@AllArgsConstructor
public class KeywordDto {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String value;
}
