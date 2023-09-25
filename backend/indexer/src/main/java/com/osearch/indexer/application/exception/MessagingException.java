package com.osearch.indexer.application.exception;

/**
 * Thrown to indicate that a messaging error occurred.
 */
public class MessagingException extends RuntimeException {

    /**
     * Constructs a new MessagingException with no detail message.
     */
    public MessagingException() {
        super();
    }

    /**
     * Constructs a new MessagingException with the specified detail message.
     *
     * @param message the detail message (which is saved for later
     *               retrieval by the getMessage() method)
     */
    public MessagingException(String message) {
        super(message);
    }

    /**
     * Constructs a new MessagingException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later
     *                retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new MessagingException with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */
    public MessagingException(Throwable cause) {
        super(cause);
    }
}
