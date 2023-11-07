package com.osearch.crawler.application.port;

import com.osearch.crawler.application.port.exception.DataAccessException;
import com.osearch.crawler.domain.entity.Page;

import java.util.Optional;

/**
 * Interface for managing pages in a repository.
 */
public interface PageRepository {

    /**
     * Finds a page with the given URL hash.
     *
     * @param hash the URL hash of the page to find.
     * @return an Optional containing the page with the specified URL hash,
     * or an empty Optional if no page is found.
     *
     * @throws DataAccessException in case of any repository error.
     */
    Optional<Page> findByUrlHash(String hash);

    /**
     * Saves the given page.
     *
     * @param page the page to save.
     *
     * @throws DataAccessException in case of any repository error.
     */
    void save(Page page);

    /**
     * Returns the count of pages saved.
     *
     * @return the count of pages.
     *
     * @throws DataAccessException in case of any repository error.
     */
    long count();
}
