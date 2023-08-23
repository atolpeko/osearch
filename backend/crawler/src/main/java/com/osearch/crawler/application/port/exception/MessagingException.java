package com.osearch.crawler.application.port.exception;

/**
 * Thrown to indicate that a messaging error occurred.
 */
public class MessagingException extends RuntimeException {

    public MessagingException() {
        super();
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagingException(Throwable cause) {
        super(cause);
    }
}
