package com.osearch.crawler.service.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class URL {
    private String value;
    private String urlHash;
    private String pageHash;
    private LocalDateTime foundAt;
    private List<String> nestedUrls;
}
