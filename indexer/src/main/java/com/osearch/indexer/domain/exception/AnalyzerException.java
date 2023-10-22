package com.osearch.indexer.domain.exception;

/**
 * Thrown when an error happens during content analysis.
 */
public class AnalyzerException extends RuntimeException {

    /**
     * Constructs a new AnalyzerException.
     */
    public AnalyzerException() {
        super();
    }

    /**
     * Constructs a new AnalyzerException with the specified detail message.
     *
     * @param message the detail message
     */
    public AnalyzerException(String message) {
        super(message);
    }

    /**
     * Constructs a new AnalyzerException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public AnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AnalyzerException with the specified cause.
     *
     * @param cause the cause
     */
    public AnalyzerException(Throwable cause) {
        super(cause);
    }
}
