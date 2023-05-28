package com.osearch.crawler.service.http.exception;

/**
 * Thrown to indicate that request response is invalid.
 */
public class HttpInvalidResponseException extends HttpServiceException {

    public HttpInvalidResponseException() {
        super();
    }

    public HttpInvalidResponseException(String message) {
        super(message);
    }

    public HttpInvalidResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
