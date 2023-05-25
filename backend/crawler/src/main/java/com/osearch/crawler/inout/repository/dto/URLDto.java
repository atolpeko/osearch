package com.osearch.crawler.inout.repository.dto;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Crawler URLs")
@Data
@AllArgsConstructor
@Builder
public class URLDto {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private final String urlHash;

    @Column(nullable = false)
    private String pageHash;

    @Column(nullable = false)
    private LocalDateTime foundAt;
}
