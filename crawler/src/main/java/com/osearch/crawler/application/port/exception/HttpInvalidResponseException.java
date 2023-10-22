package com.osearch.crawler.application.port.exception;

/**
 * Thrown to indicate that request response is invalid.
 */
public class HttpInvalidResponseException extends HttpServiceException {

    /**
     * Constructs a new HttpInvalidResponseException.
     */
    public HttpInvalidResponseException() {
        super();
    }

    /**
     * Constructs a new HttpInvalidResponseException with the specified detail message.
     *
     * @param message the detail message
     */
    public HttpInvalidResponseException(String message) {
        super(message);
    }

    /**
     * Constructs a new HttpInvalidResponseException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public HttpInvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new HttpInvalidResponseException with the specified cause.
     *
     * @param cause the cause
     */
    public HttpInvalidResponseException(Throwable cause) {
        super(cause);
    }
}
