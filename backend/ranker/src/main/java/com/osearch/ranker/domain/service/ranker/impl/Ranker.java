package com.osearch.ranker.domain.service.ranker.impl;

import com.osearch.ranker.domain.entity.Index;

/**
 * An interface used to rank pages in indexes.
 */
public interface Ranker {

    /**
     * Rank pages for an index.
     *
     * @param index  index containing pages to rank
     */
    void rank(Index index);
}
