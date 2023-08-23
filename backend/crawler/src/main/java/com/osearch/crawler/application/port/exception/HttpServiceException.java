package com.osearch.crawler.application.port.exception;

/**
 * Base rest service exception.
 */
public class HttpServiceException extends RuntimeException {

    public HttpServiceException() {
        super();
    }

    public HttpServiceException(String message) {
        super(message);
    }

    public HttpServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpServiceException(Throwable cause) {
        super(cause);
    }
}
