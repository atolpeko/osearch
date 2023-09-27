package com.osearch.crawler.application.usecase.exception;

/**
 * Thrown to indicate that crawler is already running.
 */
public class CrawlerAlreadyRunningException extends CrawlerUseCaseException {

    /**
     * Constructs a new CrawlerAlreadyRunningException.
     */
    public CrawlerAlreadyRunningException() {
        super();
    }

    /**
     * Constructs a new CrawlerAlreadyRunningException with the specified detail message.
     *
     * @param message the detail message
     */
    public CrawlerAlreadyRunningException(String message) {
        super(message);
    }

    /**
     * Constructs a new CrawlerAlreadyRunningException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public CrawlerAlreadyRunningException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CrawlerAlreadyRunningException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public CrawlerAlreadyRunningException(Throwable cause) {
        super(cause);
    }
}
