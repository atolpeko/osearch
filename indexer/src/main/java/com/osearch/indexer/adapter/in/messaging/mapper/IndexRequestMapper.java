package com.osearch.indexer.adapter.in.messaging.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.domain.valueobject.IndexRequest;

import lombok.RequiredArgsConstructor;

/**
 * A class responsible for mapping JSON strings to IndexRequest objects.
 */
@RequiredArgsConstructor
public class IndexRequestMapper {
    private final ObjectMapper mapper;

    /**
     * Maps a JSON string to an IndexRequest object.
     *
     * @param json The JSON string to be mapped.
     * @return The IndexRequest object created from the JSON string.
     *
     * @throws IllegalArgumentException If there is an error processing the JSON.
     */
    public IndexRequest map(String json) {
        try {
            return mapper.readValue(json, IndexRequest.class);
        } catch (JsonMappingException e) {
            var errorMsg = json + " doesn't match the structure of StartRequest";
            throw new IllegalArgumentException(errorMsg, e);
        } catch (JsonProcessingException e) {
            var errorMsg = json + " is not a valid JSON";
            throw new IllegalArgumentException(errorMsg, e);
        }
    }
}
