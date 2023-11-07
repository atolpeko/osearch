package com.osearch.crawler.adapter.out.repository.jpa;

import com.osearch.crawler.adapter.out.repository.dto.PageDto;

import javax.transaction.Transactional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface for manipulating PageDto objects in the database.
 */
@Repository
@Transactional
public interface PageDtoJpaRepository extends MongoRepository<PageDto, String> {

    /**
     * Finds a page by its URL hash.
     *
     * @param hash The URL hash of the page to find.
     * @return An Optional representing the PageDto found,
     * or an empty Optional if no page was found.
     */
    Optional<PageDto> findByUrlHash(String hash);
}
