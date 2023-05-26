package com.osearch.crawler.service.rest.exception;

/**
 * Thrown to indicate that client have done too many requests
 */
public class RestToManyRequestsException extends RestServiceException {

    public RestToManyRequestsException() {
        super();
    }

    public RestToManyRequestsException(String message) {
        super(message);
    }

    public RestToManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }
}
