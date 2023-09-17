package com.osearch.ranker.adapter.out.jpa;

import com.osearch.ranker.adapter.out.entity.IndexDto;
import com.osearch.ranker.adapter.out.entity.PageDto;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IndexDtoJpaRepository extends JpaRepository<IndexDto, Long> {

    Optional<IndexDto> findByKeywords(String keywords);

    @Query("SELECT p "
        + "FROM IndexDto i "
        + "INNER JOIN i.pages p "
        + "WHERE i.keywords = :index AND p.url = :pageUrl")
    Optional<PageDto> getPage(String index, String pageUrl);
}
