package com.osearch.ranker.adapter.out.entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "indexes")
public class IndexDto {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String keywords;

    @OneToMany(
        mappedBy = "index",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private Set<PageDto> pages;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        var indexDto = (IndexDto) object;
        return Objects.equals(id, indexDto.id)
            && Objects.equals(keywords, indexDto.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keywords);
    }

    @Override
    public String toString() {
        return "IndexDto{" +
            "id=" + id +
            ", keywords='" + keywords +
            '}';
    }
}
