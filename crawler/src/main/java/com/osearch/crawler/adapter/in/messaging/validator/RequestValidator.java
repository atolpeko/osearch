package com.osearch.crawler.adapter.in.messaging.validator;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import lombok.RequiredArgsConstructor;

/**
 * A class used to validate a Request object.
 */
@RequiredArgsConstructor
public class RequestValidator {

    /**
     * Validates the given request and returns a set of violations.
     *
     * @param request The request to validate.
     * @return A set of violations. Empty set indicates that the request is valid.
     */
    public Set<String> validate(Request request) {
        var operation = request.getOperation();
        if (operation == null) {
            return Set.of("Operation must be specified.");
        } else if (operation.equals(Operation.STOP)) {
            return Collections.emptySet();
        }

        var urls = request.getUrls();
        if (urls == null || urls.isEmpty() || urls.stream().anyMatch(String::isBlank)) {
            return Set.of("URLs must be specified.");
        } else if (urls.stream().anyMatch(this::isInvalidURL)) {
            return Set.of("URLs must be valid.");
        } else {
            return Collections.emptySet();
        }
    }

    private boolean isInvalidURL(String url) {
        try {
            new URL(url);
            return false;
        } catch (MalformedURLException exception) {
            return true;
        }
    }
}
