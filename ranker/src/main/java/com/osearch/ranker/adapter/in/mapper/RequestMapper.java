package com.osearch.ranker.adapter.in.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.ranker.adapter.in.entity.Request;

import lombok.RequiredArgsConstructor;

/**
 * The RequestMapper class is responsible for mapping
 * incoming JSON messages to Request objects.
 */
@RequiredArgsConstructor
public class RequestMapper {
    private final ObjectMapper objectMapper;

    /**
     * Converts a JSON message to a Request object.
     *
     * @param message The JSON message to convert.
     *
     * @return The converted Request object.
     *
     * @throws IllegalArgumentException If there is an error processing the JSON.
     */
    public Request toRequest(String message) {
        try {
            return objectMapper.readValue(message, Request.class);
        } catch (JsonMappingException e) {
            var errorMsg = message + " doesn't match the structure of Request";
            throw new IllegalArgumentException(errorMsg, e);
        } catch (JsonProcessingException e) {
            var errorMsg = message + " is not a valid JSON";
            throw new IllegalArgumentException(errorMsg, e);
        }
    }
}
