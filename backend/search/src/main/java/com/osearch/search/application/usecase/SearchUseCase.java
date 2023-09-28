package com.osearch.search.application.usecase;

import com.osearch.search.application.entity.Pageable;
import com.osearch.search.application.usecase.exception.UseCaseException;
import com.osearch.search.domain.entity.Page;

/**
 * The SearchUseCase interface provides a way
 * to search for pages based on a specific searchString.
 */
public interface SearchUseCase {

    /**
     * Searches for pages based on the given topic.
     *
     * @param searchString  the searchString to search for
     * @param offset the offset for pagination
     * @param limit  the maximum number of pages to retrieve
     *
     * @return a Pageable containing the matching pages
     *
     * @throws UseCaseException if any error happens
     */
    Pageable<Page> search(String searchString, int offset, int limit);
}
