package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.usecase.exception.PageServiceException;

/**
 * A use case class for counting the number of processed pages.
 */
public interface PageUseCase {

    /**
     * Returns the number of processed pages.
     *
     * @return the count of processed pages.
     *
     * @throws PageServiceException  if any error happens.
     */
    long countProcessed();
}
