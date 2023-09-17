package com.osearch.ranker.adapter.out.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pages")
public class PageDto {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @Column(nullable = false)
    private String url;

    @Column(name = "page_rank", nullable = false)
    private Double rank;

    @ManyToOne
    @JoinColumn(name = "index_key", nullable = false)
    private IndexDto index;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        var pageDto = (PageDto) object;
        return Objects.equals(id, pageDto.id)
            && Objects.equals(url, pageDto.url)
            && Objects.equals(rank, pageDto.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, rank);
    }

    @Override
    public String toString() {
        return "PageDto{" +
            "id=" + id +
            ", url='" + url + '\'' +
            ", rank=" + rank +
            '}';
    }
}
