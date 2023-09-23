package com.osearch.indexer.domain;

import com.osearch.indexer.domain.entity.IndexRequest;
import com.osearch.indexer.domain.entity.Page;

/**
 * IndexService is an interface for indexing pages based on the provided IndexRequest.
 */
public interface IndexService {

    /**
     * Indexes a page based on the provided IndexRequest.
     *
     * @param request The IndexRequest containing the data to be indexed.
     * @return The indexed Page object.
     */
    Page index(IndexRequest request);
}
