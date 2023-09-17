package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Page;
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
     */
    Optional<Page> findById(long id);
}
