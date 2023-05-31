package com.osearch.indexer.inout.repository.dto;

import java.time.Duration;
import java.util.HashSet;
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

    public PageDto() {
    }

    public PageDto(PageDto other) {
        this.id = other.id;
        this.url = other.url;
        this.title = other.title;
        this.loadTime = other.loadTime;
        this.isIndexed = other.isIndexed;
        this.refersTo = new HashSet<>(other.refersTo);
        this.keywordRelations = new HashSet<>(other.keywordRelations);
    }

    /**
     * Merge not null fields from passed page into this page.
     *
     * @param page page to merge
     *
     * @return product of merging passed page with this page
     */
    public PageDto merge(PageDto page) {
        var merged = new PageDto(this);
        if (page.getId() != null) {
            merged.setId(page.getId());
        }
        if (page.getUrl() != null) {
            merged.setUrl(page.getUrl());
        }
        if (page.getTitle() != null) {
            merged.setTitle(page.getTitle());
        }
        if (page.getLoadTime() != null) {
            merged.setLoadTime(page.getLoadTime());
        }
        if (page.getIsIndexed() != null) {
            merged.setIsIndexed(page.getIsIndexed());
        }
        if (page.getRefersTo() != null) {
            merged.setRefersTo(page.getRefersTo());
        }
        if (page.getKeywordRelations() != null) {
            merged.setKeywordRelations(page.getKeywordRelations());
        }

        return merged;
    }
}
