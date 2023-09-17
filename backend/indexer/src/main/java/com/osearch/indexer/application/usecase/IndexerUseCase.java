package com.osearch.indexer.application.usecase;

import com.osearch.indexer.domain.entity.IndexRequest;

/**
 * Used to process index request.
 */
public interface IndexerUseCase {

    /**
     * Process index request.
     *
     * @param request  index request
     */
    void process(IndexRequest request);
}
