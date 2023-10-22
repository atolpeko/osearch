package com.osearch.crawler.application.usecase.exception;

/**
 * Base use case exception.
 */
public abstract class UseCaseException extends RuntimeException {

    /**
     * Constructs a new UseCaseException.
     */
    protected UseCaseException() {
        super();
    }

    /**
     * Constructs a new UseCaseException with the specified detail message.
     *
     * @param message the detail message
     */
    protected UseCaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new UseCaseException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    protected UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UseCaseException with the specified cause.
     *
     * @param cause the cause
     */
    protected UseCaseException(Throwable cause) {
        super(cause);
    }
}
