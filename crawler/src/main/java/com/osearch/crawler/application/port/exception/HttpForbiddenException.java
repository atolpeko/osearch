package com.osearch.crawler.application.port.exception;

/**
 * Thrown to indicate that request got HTTP 403 Forbidden.
 */
public class HttpForbiddenException extends HttpServiceException {

    /**
     * Constructs a new HttpForbiddenException.
     */
    public HttpForbiddenException() {
        super();
    }

    /**
     * Constructs a new HttpForbiddenException with the specified detail message.
     *
     * @param message the detail message
     */
    public HttpForbiddenException(String message) {
        super(message);
    }

    /**
     * Constructs a new HttpForbiddenException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public HttpForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new HttpForbiddenException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public HttpForbiddenException(Throwable cause) {
        super(cause);
    }
}
