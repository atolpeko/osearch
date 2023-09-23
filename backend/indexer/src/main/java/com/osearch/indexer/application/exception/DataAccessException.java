package com.osearch.indexer.application.exception;

/**
 * Repository data access exception.
 */
public class DataAccessException extends RuntimeException {

    /**
     * Constructs a new DataAccessException with no specified detail message.
     */
    public DataAccessException() {
        super();
    }

    /**
     * Constructs a new DataAccessException with the specified detail message.
     *
     * @param message the detail message (which is saved for later
     *               retrieval by the getMessage() method)
     */
    public DataAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new DataAccessException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later
     *                retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval
     *             by the getCause() method). A null value is permitted
     *              and indicates that the cause is nonexistent or unknown.
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DataAccessException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval
     *             by the getCause() method). A null value is permitted
     *              and indicates that the cause is nonexistent or unknown.
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
