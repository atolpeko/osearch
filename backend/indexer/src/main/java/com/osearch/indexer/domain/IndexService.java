package com.osearch.indexer.domain;

import com.osearch.indexer.domain.exception.AnalyzerException;
import com.osearch.indexer.domain.exception.UnsupportedLocaleException;
import com.osearch.indexer.domain.valueobject.IndexRequest;
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
     *
     * @throws AnalyzerException if an error happens during content analysis.
     * @throws UnsupportedLocaleException if content locale is not supported for indexing.
     */
    Page index(IndexRequest request);
}
