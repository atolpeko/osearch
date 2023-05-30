package com.osearch.indexer.service.analyzer;

import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Page;

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
