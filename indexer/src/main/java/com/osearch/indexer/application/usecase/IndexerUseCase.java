package com.osearch.indexer.application.usecase;

import com.osearch.indexer.application.usecase.exception.IndexerException;
import com.osearch.indexer.domain.valueobject.IndexRequest;

/**
 * IndexerUseCase interface provides a method to process index requests.
 */
public interface IndexerUseCase {

    /**
     * Process the given IndexRequest.
     *
     * @param request the IndexRequest to process.
     *
     * @throws IndexerException if any error happens.
     */
    void process(IndexRequest request);

    /**
     * Returns the count of indexed pages.
     *
     * @return the count of indexed pages.
     *
     * @throws IndexerException if any error happens.
     */
    int countIndexed();
}
