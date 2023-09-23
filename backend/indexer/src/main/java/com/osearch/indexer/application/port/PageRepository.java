package com.osearch.indexer.application.port;

import com.osearch.indexer.application.exception.DataAccessException;
import com.osearch.indexer.domain.entity.Page;

/**
 * Used to work with stored pages.
 */
public interface PageRepository {

    /**
     * Save a page.
     *
     * @param page  page to save.
     *
     * @return generated page ID.
     *
     * @throws DataAccessException if any error happens.
     */
    Long save(Page page);
}
