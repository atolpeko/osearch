package com.osearch.crawler.application.usecase.exception;

/**
 * Thrown to indicate that crawler service is already running.
 */
public class CrawlerAlreadyRunningException extends CrawlerServiceException {

    public CrawlerAlreadyRunningException() {
        super();
    }

    public CrawlerAlreadyRunningException(String message) {
        super(message);
    }
}
