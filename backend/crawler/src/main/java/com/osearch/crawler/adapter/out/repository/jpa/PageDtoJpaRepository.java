package com.osearch.crawler.adapter.out.repository.jpa;

import com.osearch.crawler.adapter.out.repository.dto.PageDto;

import javax.transaction.Transactional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Used to work with PageDto objects stored in mongo.
 */
@Repository
@Transactional
public interface PageDtoJpaRepository extends MongoRepository<PageDto, String> {

    /**
     * Find PageDto by its hash.
     *
     * @param hash  URL hash of the PageDto to find
     *
     * @return optional of found PageDto or Optional.empty()
     */
    Optional<PageDto> findByUrlHash(String hash);
}
