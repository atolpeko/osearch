package com.osearch.crawler.service.http.exception;

/**
 * Thrown to indicate that client have done too many requests.
 */
public class HttpToManyRequestsException extends HttpServiceException {

    public HttpToManyRequestsException() {
        super();
    }

    public HttpToManyRequestsException(String message) {
        super(message);
    }

    public HttpToManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }
}
