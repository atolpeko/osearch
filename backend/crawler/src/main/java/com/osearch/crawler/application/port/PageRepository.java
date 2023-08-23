package com.osearch.crawler.application.port;

import com.osearch.crawler.domain.entity.Page;
import java.util.Optional;

/**
 * Used to work with stored Page objects.
 */
public interface PageRepository {

    /**
     * Find Page by its hash.
     *
     * @param hash  URL hash of the Page to find
     *
     * @return optional of Page or Optional.empty()
     */
    Optional<Page> findByUrlHash(String hash);

    /**
     * Save page.
     *
     * @param page  page to save
     */
    void save(Page page);

    /**
     * Count saved pages.
     *
     * @return  the number of pages
     */
    long count();
}
