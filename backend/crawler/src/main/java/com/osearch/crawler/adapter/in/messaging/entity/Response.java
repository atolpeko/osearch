package com.osearch.crawler.adapter.in.messaging.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents a response being sent via Kafka after request.
 */
@Data
@Builder
@EqualsAndHashCode(exclude = "requestDateTime")
public class Response implements Serializable {

    /**
     * Status enumeration represents the status of request.
     */
    public enum Status { SUCCESSFUL, ERROR }

    private final LocalDateTime requestDateTime;
    private final Status status;
    private String description;

    @Override
    public String toString() {
        return "{" +
            "requestDateTime: " + requestDateTime +
            ", status: " + status +
            ", description: " + description +
            "}";
    }
}
