package com.osearch.ranker.application.usecase;

/**
 * This interface represents the use case for ranking a page.
 * It provides a method to process a page with the specified ID.
 */
public interface RankerUseCase {

    /**
     * The process method processes the given page ID.
     *
     * @param pageId The ID of the page to be processed.
     */
    void process(long pageId);
}
