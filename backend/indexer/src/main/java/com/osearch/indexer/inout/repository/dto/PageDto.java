package com.osearch.indexer.inout.repository.dto;

import java.time.Duration;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Node("Page")
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "refersTo")
@ToString(exclude = "refersTo")
public class PageDto {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String url;

    @NotBlank
    private String title;

    @NotNull
    private Duration loadTime;

    @NotNull
    private Boolean isIndexed;

    @Relationship(type = "REFERS_TO", direction = Direction.OUTGOING)
    private Set<PageDto> refersTo;

    @Relationship(type = "HAS",  direction = Direction.OUTGOING)
    private Set<KeywordRelationDto> keywordRelations;

    /**
     * Merge not null fields from passed page into this page.
     *
     * @param page page to merge
     */
    public void merge(PageDto page) {
        if (page.getUrl() != null) {
            setUrl(page.getUrl());
        }
        if (page.getTitle() != null) {
            setTitle(page.getTitle());
        }
        if (page.getLoadTime() != null) {
            setLoadTime(page.getLoadTime());
        }
        if (page.getIsIndexed() != null) {
            setIsIndexed(page.getIsIndexed());
        }
        if (page.getRefersTo() != null) {
            setRefersTo(page.getRefersTo());
        }
        if (page.getKeywordRelations() != null) {
            setKeywordRelations(page.getKeywordRelations());
        }
    }
}
