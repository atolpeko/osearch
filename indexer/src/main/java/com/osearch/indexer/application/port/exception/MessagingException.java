package com.osearch.indexer.application.port.exception;

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
     * @param message the detail message
     */
    public MessagingException(String message) {
        super(message);
    }

    /**
     * Constructs a new MessagingException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new MessagingException with the specified cause.
     *
     * @param cause the cause
     */
    public MessagingException(Throwable cause) {
        super(cause);
    }
}
