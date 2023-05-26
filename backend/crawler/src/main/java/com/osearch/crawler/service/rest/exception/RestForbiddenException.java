package com.osearch.crawler.service.rest.exception;

/**
 * Thrown to indicate that rest request got HTTP 403 Forbidden
 */
public class RestForbiddenException extends RestServiceException {

    public RestForbiddenException() {
        super();
    }

    public RestForbiddenException(String message) {
        super(message);
    }

    public RestForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
