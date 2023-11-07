package com.osearch.crawler.application.usecase.exception;

/**
 * Base crawler use case exception.
 */
public class CrawlerUseCaseException extends UseCaseException {

    /**
     * Constructs a new CrawlerUseCaseException.
     */
    public CrawlerUseCaseException() {
        super();
    }

    /**
     * Constructs a new CrawlerUseCaseException with the specified detail message.
     *
     * @param message the detail message
     */
    public CrawlerUseCaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new CrawlerUseCaseException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public CrawlerUseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CrawlerUseCaseException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public CrawlerUseCaseException(Throwable cause) {
        super(cause);
    }
}
