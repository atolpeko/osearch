package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.application.port.exception.DataAccessException;

import java.util.Optional;

/**
 * Used to work with stored pages.
 */
public interface PageRepository {

    /**
     * Find a page by its ID.
     *
     * @param id  page ID
     *
     * @return found page if it exists or Optional.empty() otherwise
     *
     * @throws DataAccessException if any repository exception occurred
     */
    Optional<Page> findById(long id);
}
