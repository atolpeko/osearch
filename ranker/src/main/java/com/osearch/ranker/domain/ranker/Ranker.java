package com.osearch.ranker.domain.ranker;

import com.osearch.ranker.domain.entity.Index;

/**
 * The Ranker interface defines the contract for classes that implement
 * page ranking algorithms.It provides methods to rank pages for an index
 * and set the next ranker in the chain.
 */
public interface Ranker {

    /**
     * Rank pages for an index.
     *
     * @param index  the index containing pages to rank.
     */
    void rank(Index index);

    /**
     * Sets the next ranker in the chain.
     *
     * @param ranker the ranker to set as the next ranker.
     */
    void setNext(Ranker ranker);
}
