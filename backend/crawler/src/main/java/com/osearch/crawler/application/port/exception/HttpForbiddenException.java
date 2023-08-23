package com.osearch.crawler.application.port.exception;

/**
 * Thrown to indicate that request got HTTP 403 Forbidden.
 */
public class HttpForbiddenException extends HttpServiceException {

    public HttpForbiddenException() {
        super();
    }

    public HttpForbiddenException(String message) {
        super(message);
    }

    public HttpForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
