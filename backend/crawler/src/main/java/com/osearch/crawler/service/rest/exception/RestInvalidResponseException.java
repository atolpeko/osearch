package com.osearch.crawler.service.rest.exception;

/**
 * Thrown to indicate that request response is invalid
 */
public class RestInvalidResponseException extends RestServiceException {

    public RestInvalidResponseException() {
        super();
    }

    public RestInvalidResponseException(String message) {
        super(message);
    }

    public RestInvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
