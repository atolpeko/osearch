package com.osearch.ranker.adapter.out.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pages")
public class PageDto {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(name = "page_rank", nullable = false)
    private Double rank;

    @ManyToOne
    @JoinColumn(name = "index_key", nullable = false)
    private IndexDto index;
}
