package com.osearch.crawler.application.usecase.exception;

/**
 * Thrown to indicate that the crawler is not running.
 */
public class CrawlerNotRunningException extends CrawlerUseCaseException {

    /**
     * Constructs a new CrawlerNotRunningException.
     */
    public CrawlerNotRunningException() {
        super();
    }

    /**
     * Constructs a new CrawlerNotRunningException with the specified message.
     *
     * @param message the detail message
     */
    public CrawlerNotRunningException(String message) {
        super(message);
    }

    /**
     * Constructs a new CrawlerNotRunningException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public CrawlerNotRunningException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CrawlerNotRunningException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public CrawlerNotRunningException(Throwable cause) {
        super(cause);
    }
}
