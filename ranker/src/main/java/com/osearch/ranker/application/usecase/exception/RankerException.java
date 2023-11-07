package com.osearch.ranker.application.usecase.exception;

/**
 * RankerException use case exception.
 */
public class RankerException extends RuntimeException {

    /**
     * Constructs a new RankerException.
     */
    public RankerException() {
        super();
    }

    /**
     * Constructs a new RankerException with the specified detail message.
     *
     * @param message the detail message
     */
    public RankerException(String message) {
        super(message);
    }

    /**
     * Constructs a new RankerException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public RankerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new RankerException with the specified cause.
     *
     * @param cause the cause
     */
    public RankerException(Throwable cause) {
        super(cause);
    }
}
