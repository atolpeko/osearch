package com.osearch.ranker.application.port.exception;

/**
 * Thrown to indicate that a data modification error occurred.
 */
public class DataModificationException extends RuntimeException {

    public DataModificationException() {
        super();
    }

    public DataModificationException(String message) {
        super(message);
    }

    public DataModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataModificationException(Throwable cause) {
        super(cause);
    }
}
