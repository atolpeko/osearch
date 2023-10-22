package com.osearch.crawler.application.port.exception;

/**
 * Thrown to indicate that a messaging error occurred.
 */
public class MessagingException extends RuntimeException {

    /**
     * Construct a new MessagingException.
     */
    public MessagingException() {
        super();
    }

    /**
     * Constructs a new MessagingException with the given message.
     *
     * @param message the detail message
     */
    public MessagingException(String message) {
        super(message);
    }

    /**
     * Constructs a new MessagingException with the given message and cause.
     *
     * @param message the detail message
     * @param cause the cause of this exception
     */
    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new MessagingException with the given cause.
     *
     * @param cause the cause of this exception
     */
    public MessagingException(Throwable cause) {
        super(cause);
    }
}
