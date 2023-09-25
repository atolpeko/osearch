package com.osearch.indexer.application.usecase;

import com.osearch.indexer.application.exception.DataAccessException;
import com.osearch.indexer.application.exception.MessagingException;
import com.osearch.indexer.domain.entity.IndexRequest;

/**
 * IndexerUseCase interface provides a method to process index requests.
 */
public interface IndexerUseCase {

    /**
     * Process the given IndexRequest.
     *
     * @param request the IndexRequest to process.
     *
     * @throws MessagingException  if kafka messaging error happens.
     * @throws DataAccessException if any repository error happens.
     */
    void process(IndexRequest request);

    /**
     * Returns the count of indexed pages.
     *
     * @return the count of indexed pages.
     *
     * @throws DataAccessException if any repository error happens.
     */
    int countIndexed();
}
