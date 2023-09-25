package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.application.port.exception.DataAccessException;

import java.util.Optional;

/**
 * The PageRepository interface provides methods to interact
 * with a repository of Page objects.
 */
public interface PageRepository {

    /**
     * Find a page by its ID.
     *
     * @param id  the page ID.
     *
     * @return found page if it exists or Optional.empty() otherwise.
     *
     * @throws DataAccessException if any repository exception occurred.
     */
    Optional<Page> findById(long id);
}
