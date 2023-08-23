package com.osearch.crawler.application.usecase.exception;

/**
 * Base crawler service exception.
 */
public class CrawlerServiceException extends ServiceException {

    public CrawlerServiceException() {
        super();
    }

    public CrawlerServiceException(String message) {
        super(message);
    }

    public CrawlerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrawlerServiceException(Throwable cause) {
        super(cause);
    }
}
