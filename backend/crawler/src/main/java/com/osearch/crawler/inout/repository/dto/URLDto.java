package com.osearch.crawler.inout.repository.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "crawler-urls")
@Data
@AllArgsConstructor
@Builder
public class URLDto {

    @Id
    private String id;

    @NotBlank(message = "URL hash is required")
    private final String urlHash;

    @NotBlank(message = "Page hash is required")
    private String pageHash;

    @NotBlank(message = "Found at is required")
    private LocalDateTime foundAt;
}
