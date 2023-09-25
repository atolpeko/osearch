package com.osearch.ranker.application.port.exception;

/**
 * Thrown to indicate that a data modification error occurred.
 */
public class DataModificationException extends RuntimeException {

    /**
     * This class represents an exception that is thrown when there is an error
     * during data modification.
     *
     * @since 1.0
     */
    public DataModificationException() {
        super();
    }

    /**
     * Exception thrown when an error occurs while modifying repository data.
     *
     * @param message the detail message.
     */
    public DataModificationException(String message) {
        super(message);
    }

    /**
     * Exception thrown when an error occurs while modifying repository data.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public DataModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception thrown when an error occurs while modifying repository data.
     *
     * @param cause the cause of the exception.
     */
    public DataModificationException(Throwable cause) {
        super(cause);
    }
}
