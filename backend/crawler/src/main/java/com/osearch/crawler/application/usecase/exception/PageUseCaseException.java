package com.osearch.crawler.application.usecase.exception;

/**
 * Page use case exception.
 */
public class PageUseCaseException extends UseCaseException {

    /**
     * Constructs a new PageUseCaseException.
     */
    public PageUseCaseException() {
        super();
    }

    /**
     * Constructs a new PageUseCaseException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PageUseCaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new PageUseCaseException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause
     */
    public PageUseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new PageUseCaseException with the specified cause.
     *
     * @param cause the cause
     */
    public PageUseCaseException(Throwable cause) {
        super(cause);
    }
}
