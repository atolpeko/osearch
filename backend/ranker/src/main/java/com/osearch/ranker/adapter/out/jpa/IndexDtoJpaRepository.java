package com.osearch.ranker.adapter.out.jpa;

import com.osearch.ranker.adapter.out.entity.IndexDto;

import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface IndexDtoJpaRepository extends JpaRepository<IndexDto, Long> {

    Optional<IndexDto> findByKeywords(String keywords);
}
