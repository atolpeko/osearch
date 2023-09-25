package com.osearch.ranker.domain.service;

import com.osearch.ranker.domain.entity.Index;
import lombok.Setter;

/**
 * BaseRanker is an abstract class that provides the basic functionality
 * for implementing a Ranker in a chain of Rankers.
 */
@Setter
public abstract class BaseRanker implements Ranker {
    private Ranker next;

    /**
     * Calls the `rank` method of the next Ranker in the chain, if it exists.
     *
     * @param index the Index object to be passed to the next Ranker
     */
    protected void next(Index index) {
        if (next != null) {
            next.rank(index);
        }
    }
}
