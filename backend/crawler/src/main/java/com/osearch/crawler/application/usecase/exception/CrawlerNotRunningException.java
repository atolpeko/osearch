package com.osearch.crawler.application.usecase.exception;

/**
 * Thrown to indicate that crawler service is not running.
 */
public class CrawlerNotRunningException extends CrawlerServiceException {

    public CrawlerNotRunningException() {
        super();
    }

    public CrawlerNotRunningException(String message) {
        super(message);
    }
}
