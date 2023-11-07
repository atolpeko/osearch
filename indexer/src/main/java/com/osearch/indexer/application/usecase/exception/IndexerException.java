package com.osearch.indexer.application.usecase.exception;

/**
 * Indexer use case exception.
 */
public class IndexerException extends RuntimeException {

    /**
     * Constructs a new IndexerException.
     */
    public IndexerException() {
        super();
    }

    /**
     * Constructs a new IndexerException with the specified detail message.
     *
     * @param message the detail message
     */
    public IndexerException(String message) {
        super(message);
    }

    /**
     * Constructs a new IndexerException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public IndexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new IndexerException with the specified cause.
     *
     * @param cause the cause
     */
    public IndexerException(Throwable cause) {
        super(cause);
    }
}
