package com.osearch.crawler.application.usecase.exception;

/**
 * Page service exception.
 */
public class PageServiceException extends ServiceException {

    public PageServiceException() {
        super();
    }

    public PageServiceException(String message) {
        super(message);
    }

    public PageServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageServiceException(Throwable cause) {
        super(cause);
    }
}
