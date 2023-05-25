package com.osearch.crawler.service.exception;

/**
 * Base crawler service exception
 */
public class CrawlerServiceException extends RuntimeException {

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
