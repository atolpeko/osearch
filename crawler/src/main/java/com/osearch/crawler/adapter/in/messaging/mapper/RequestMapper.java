package com.osearch.crawler.adapter.in.messaging.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.validator.RequestValidator;

import lombok.RequiredArgsConstructor;

/**
 * The RequestMapper class is responsible for mapping
 * incoming JSON messages to Request objects.
 */
@RequiredArgsConstructor
public class RequestMapper {
    private final ObjectMapper objectMapper;
    private final RequestValidator validator;

    /**
     * Converts a JSON message to a Request object.
     *
     * @param message The JSON message to convert.
     *
     * @return The converted Request object.
     *
     * @throws IllegalArgumentException If there is an error processing the JSON or
     * if the converted Request object is not valid.
     */
    public Request toRequest(String message) {
        try {
            var request = objectMapper.readValue(message, Request.class);
            var violations = validator.validate(request);
            if (!violations.isEmpty()) {
                var errorMsg = new StringBuilder(message + " is not a valid request: ");
                for (var violation: violations) {
                    errorMsg.append(violation);
                }

                throw new IllegalArgumentException(errorMsg.toString());
            }

            return request;
        } catch (JsonMappingException e) {
            var errorMsg = message + " doesn't match the structure of StartRequest";
            throw new IllegalArgumentException(errorMsg, e);
        } catch (JsonProcessingException e) {
            var errorMsg = message + " is not a valid JSON";
            throw new IllegalArgumentException(errorMsg, e);
        }
    }
}
