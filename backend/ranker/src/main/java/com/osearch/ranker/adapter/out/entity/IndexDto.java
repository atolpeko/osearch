package com.osearch.ranker.adapter.out.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "indexes")
public class IndexDto {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String keywords;

    @OneToMany(mappedBy = "index")
    private Set<PageDto> pages;
}
