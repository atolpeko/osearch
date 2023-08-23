package com.osearch.crawler.adapter.in.messaging.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(exclude = "requestDateTime")
public class Response implements Serializable {
    public enum Status { SUCCESSFUL, ERROR }

    private LocalDateTime requestDateTime;
    private Status status;
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
