package com.osearch.indexer.domain.exception;

/**
 * Thrown to indicate that a locale is not supported for indexing.
 */
public class UnsupportedLocaleException extends RuntimeException {

    /**
     * Constructs a new UnsupportedLocaleException.
     */
    public UnsupportedLocaleException() {
        super();
    }

    /**
     * Constructs a new UnsupportedLocaleException with the specified detail message.
     *
     * @param message the detail message
     */
    public UnsupportedLocaleException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnsupportedLocaleException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public UnsupportedLocaleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UnsupportedLocaleException with the specified cause.
     *
     * @param cause the cause of this exception
     */
    public UnsupportedLocaleException(Throwable cause) {
        super(cause);
    }
}
