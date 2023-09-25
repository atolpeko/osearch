package com.osearch.indexer.domain.exception;

/**
 * Thrown to indicate that a locale is not supported for indexing.
 */
public class UnsupportedLocaleException extends RuntimeException {

    /**
     * This exception is thrown when a locale is not supported by the application.
     */
    public UnsupportedLocaleException() {
        super();
    }

    /**
     * This exception is thrown when a locale is not supported by the application.
     *
     * @param message  the detail message.
     */
    public UnsupportedLocaleException(String message) {
        super(message);
    }

    /**
     * This exception is thrown when a locale is not supported by the application.
     *
     * @param message  the detail message.
     * @param cause    the cause of the exception.
     */
    public UnsupportedLocaleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * This exception is thrown when a locale is not supported by the application.
     *
     * @param cause the cause.
     */
    public UnsupportedLocaleException(Throwable cause) {
        super(cause);
    }
}
