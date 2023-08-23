package com.osearch.crawler.adapter.in.messaging.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.validator.RequestValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestMapper {
    private final ObjectMapper objectMapper;
    private final RequestValidator validator;

    public Request toRequest(String message) throws JsonProcessingException {
        var request = objectMapper.readValue(message, Request.class);
        if (!validator.isValid(request)) {
            throw new IllegalArgumentException();
        }

        return request;
    }
}
