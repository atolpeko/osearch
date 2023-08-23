package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.usecase.exception.PageServiceException;

public interface PageUseCase {

    /**
     * @return the number of processed pages
     *
     * @throws PageServiceException  if any error happens
     */
    long countProcessed();
}
