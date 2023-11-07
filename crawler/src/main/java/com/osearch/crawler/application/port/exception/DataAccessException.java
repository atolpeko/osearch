package com.osearch.crawler.application.port.exception;

/**
 * Repository data access exception.
 */
public class DataAccessException extends RuntimeException {

    /**
     * Construct a new Repository data access exception.
     */
    public DataAccessException() {
        super();
    }

    /**
     * Constructs a new Repository data access exception with the given message.
     *
     * @param message the detail message
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new Repository data access exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new Repository data access exception with the given cause.
     *
     * @param cause the cause of this exception
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
