package com.osearch.crawler.application.port.exception;

/**
 * Thrown to indicate that the service have done too many requests.
 */
public class HttpToManyRequestsException extends HttpServiceException {

    /**
     * Constructs a new HttpToManyRequestsException.
     */
    public HttpToManyRequestsException() {
        super();
    }

    /**
     * Constructs a new HttpToManyRequestsException with the specified detail message.
     *
     * @param message the detail message
     */
    public HttpToManyRequestsException(String message) {
        super(message);
    }

    /**
     * Constructs a new HttpToManyRequestsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public HttpToManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new HttpToManyRequestsException with the specified cause.
     *
     * @param cause the cause
     */
    public HttpToManyRequestsException(Throwable cause) {
        super(cause);
    }
}
