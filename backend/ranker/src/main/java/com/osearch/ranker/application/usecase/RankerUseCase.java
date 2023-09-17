package com.osearch.ranker.application.usecase;

/**
 * Used to rank pages.
 */
public interface RankerUseCase {

    /**
     * Process page with the specified ID.
     *
     * @param pageId  page ID
     */
    void process(long pageId);
}
