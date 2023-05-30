package com.osearch.indexer.service;

import com.osearch.indexer.service.entity.IndexRequest;

/**
 * Used to process index request.
 */
public interface IndexerService {

    /**
     * Process index request.
     *
     * @param request  index request
     */
    void process(IndexRequest request);
}
