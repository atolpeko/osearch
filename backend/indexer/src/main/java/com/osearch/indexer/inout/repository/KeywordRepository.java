package com.osearch.indexer.inout.repository;

import com.osearch.indexer.inout.repository.dto.KeywordDto;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to work with KeywordDto objects stored in neo.
 */
@Repository
@Transactional
public interface KeywordRepository extends Neo4jRepository<KeywordDto, Long> {

    Optional<KeywordDto> findByValue(String value);
}
