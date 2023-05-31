package com.osearch.indexer.inout.repository.dto;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@Builder
public class KeywordRelationDto {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private KeywordDto keyword;

    private Long occurrences;
}