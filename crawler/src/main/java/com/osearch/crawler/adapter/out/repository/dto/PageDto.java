package com.osearch.crawler.adapter.out.repository.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

/**
 * Represents a page stored in Mongo.
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "foundAt")
@Document(collection = "crawler-urls")
public class PageDto {

    @Id
    @NotBlank(message = "URL hash is required")
    private final String urlHash;

    @NotBlank(message = "Page hash is required")
    private String hash;

    @NotBlank(message = "Found at is required")
    private LocalDateTime foundAt;
}
