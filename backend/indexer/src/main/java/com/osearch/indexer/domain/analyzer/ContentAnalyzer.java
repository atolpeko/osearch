package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.entity.IndexRequest;
import com.osearch.indexer.domain.entity.Page;

/**
 * Used to process index request and assemble page object.
 */
public interface ContentAnalyzer {

    /**
     * Process index request and assemble page object
     *
     * @param request  request to process
     *
     * @return Page object
     */
    Page analyze(IndexRequest request);
}
