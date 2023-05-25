package com.osearch.crawler.inout.messaging.validator;

import com.osearch.crawler.inout.messaging.entity.Request;
import com.osearch.crawler.inout.messaging.entity.Request.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
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
