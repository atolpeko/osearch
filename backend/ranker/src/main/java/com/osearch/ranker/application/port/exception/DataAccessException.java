package com.osearch.ranker.application.port.exception;

/**
 * Thrown to indicate that a repository data access error occurred.
 */
public class DataAccessException extends RuntimeException {

    /**
     * Exception thrown when an error occurs while accessing data.
     */
    public DataAccessException() {
        super();
    }

    /**
     * Exception thrown when an error occurs while accessing repository data.
     *
     * @param message the detail message.
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Exception thrown when an error occurs while accessing repository data.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when an error occurs while accessing repository data.
     *
     * @param cause the cause of the exception.
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
