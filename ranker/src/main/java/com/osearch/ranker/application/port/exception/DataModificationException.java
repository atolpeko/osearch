package com.osearch.ranker.application.port.exception;

/**
 * Thrown to indicate that a data modification error occurred.
 */
public class DataModificationException extends RuntimeException {

    /**
     * Constructs a new DataModificationException.
     */
    public DataModificationException() {
        super();
    }

    /**
     * Constructs a new DataModificationException with the specified message.
     *
     * @param message the detail message
     */
    public DataModificationException(String message) {
        super(message);
    }

    /**
     * Constructs a new DataModificationException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public DataModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DataModificationException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public DataModificationException(Throwable cause) {
        super(cause);
    }
}
