package com.osearch.indexer.inout.repository;

import com.osearch.indexer.inout.repository.dto.PageDto;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to work with PageDto objects stored in neo.
 */
@Repository
@Transactional
public interface PageRepository extends Neo4jRepository<PageDto, Long> {

    Optional<PageDto> findByUrl(String url);
}
