package com.osearch.ranker.domain.service.ranker;

import com.osearch.ranker.domain.entity.Index;

/**
 * An interface used to rank pages.
 */
public interface RankerService {

    /**
     * Rank pages for an index.
     *
     * @param  index index containing pages to rank
     */
    void rank(Index index);
}
