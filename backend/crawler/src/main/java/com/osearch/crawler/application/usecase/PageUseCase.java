package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.usecase.exception.PageUseCaseException;

/**
 * The PageUseCase interface defines the methods for accessing
 * crawled pages info.
 */
public interface PageUseCase {

    /**
     * Returns the number of crawled pages.
     *
     * @return the count of crawled pages.
     *
     * @throws PageUseCaseException  if any error happens.
     */
    long countCrawled();
}
