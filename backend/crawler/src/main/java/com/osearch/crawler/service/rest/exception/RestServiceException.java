package com.osearch.crawler.service.rest.exception;

/**
 * Base rest service exception
 */
public class RestServiceException extends RuntimeException {

    public RestServiceException() {
        super();
    }

    public RestServiceException(String message) {
        super(message);
    }

    public RestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestServiceException(Throwable cause) {
        super(cause);
    }
}
