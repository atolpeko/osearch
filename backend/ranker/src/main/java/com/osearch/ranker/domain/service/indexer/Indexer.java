package com.osearch.ranker.domain.service.indexer;

import com.osearch.ranker.domain.entity.Page;

import java.util.Set;

/**
 * An interface used to parse page and get indexes.
 */
public interface Indexer {

    /**
     * Create indexes.
     *
     * @param page page to index
     */
    Set<String> index(Page page);
}
