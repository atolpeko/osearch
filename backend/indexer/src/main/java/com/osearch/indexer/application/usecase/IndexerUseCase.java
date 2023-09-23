package com.osearch.indexer.application.usecase;

import com.osearch.indexer.domain.entity.IndexRequest;

/**
 * IndexerUseCase interface provides a method to process index requests.
 */
public interface IndexerUseCase {

    /**
     * Process the given IndexRequest.
     *
     * @param request the IndexRequest to process.
     */
    void process(IndexRequest request);
}
