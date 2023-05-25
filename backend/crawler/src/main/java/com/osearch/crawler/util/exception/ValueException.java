package com.osearch.crawler.util.exception;

import lombok.Getter;

@Getter
public class ValueException extends RuntimeException {
    private final Object value;

    public ValueException(Object value, String message) {
        super(message);
        this.value = value;
    }

    public ValueException(Object value, String message, Throwable cause) {
        super(message, cause);
        this.value = value;
    }
}
