package com.osearch.indexer.inout.repository.dto;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

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

    private String url;
    private String title;
    private Duration loadTime;
    private Boolean isIndexed;

    @Relationship(type = "REFERS_TO", direction = Direction.OUTGOING)
    private Set<PageDto> refersTo;

    @Relationship(type = "HAS", direction = Direction.OUTGOING)
    private Set<KeywordDto> keywords;

    @Relationship(type = "META", direction = Direction.OUTGOING)
    private Set<HTMLElementDto> metaTags;

    public PageDto() {
    }

    public PageDto(PageDto other) {
        this.id = other.id;
        this.url = other.url;
        this.title = other.title;
        this.loadTime = other.loadTime;
        this.isIndexed = other.isIndexed;
        this.refersTo = new HashSet<>(other.refersTo);
        this.keywords = new HashSet<>(other.keywords);
        this.metaTags = new HashSet<>(other.metaTags);
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
        if (page.getKeywords() != null) {
            merged.setKeywords(page.getKeywords());
        }
        if (page.getMetaTags() != null) {
            merged.setMetaTags(page.getMetaTags());
        }

        return merged;
    }
}
