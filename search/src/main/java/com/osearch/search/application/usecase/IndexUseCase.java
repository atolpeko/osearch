package com.osearch.search.application.usecase;

import com.osearch.search.application.entity.Pageable;
import com.osearch.search.application.usecase.exception.UseCaseException;
import com.osearch.search.domain.entity.IndexInfo;

/**
 * The IndexUseCase interface represents a use case for retrieving
 * information about indexes with pagination.
 */
public interface IndexUseCase {

    /**
     * Retrieves information about indexes with pagination.
     *
     * @param offset The starting offset of the indexes.
     * @param limit The maximum number of indexes to retrieve.
     *
     * @return A Pageable object containing the indexes information.
     *
     * @throws UseCaseException if any error happens
     */
    Pageable<IndexInfo> getInfo(int offset, int limit);
}
