package com.osearch.crawler.application.port.exception;

/**
 * Base HTTP service exception.
 */
public class HttpServiceException extends RuntimeException {

    /**
     * Constructs a new HttpServiceException.
     */
    public HttpServiceException() {
        super();
    }

    /**
     * Constructs a new HttpServiceException with the specified detail message.
     *
     * @param message the detail message
     */
    public HttpServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new HttpServiceException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public HttpServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new HttpServiceException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public HttpServiceException(Throwable cause) {
        super(cause);
    }
}
