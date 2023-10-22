package com.osearch.search.application.usecase.exception;

/**
 * UseCaseException exception.
 */
public class UseCaseException extends RuntimeException {

    /**
     * Constructs a new UseCaseException.
     */
    public UseCaseException() {
        super();
    }

    /**
     * Constructs a new UseCaseException with the specified detail message.
     *
     * @param message the detail message
     */
    public UseCaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new UseCaseException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UseCaseException with the specified cause.
     *
     * @param cause the cause
     */
    public UseCaseException(Throwable cause) {
        super(cause);
    }
}
