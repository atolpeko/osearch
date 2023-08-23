package com.osearch.crawler.adapter.in.messaging.validator;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestValidator {

    public boolean isValid(Request request) {
        var operation = request.getOperation();
        if (operation == null) {
            return false;
        }

        if (operation.equals(Operation.STOP)) {
            return true;
        }

        var urls = request.getUrls();
        if (urls == null || urls.isEmpty()) {
            return false;
        }

        return urls.stream().noneMatch(String::isBlank);
    }
}
