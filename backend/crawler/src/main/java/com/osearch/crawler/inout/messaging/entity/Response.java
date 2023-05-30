package com.osearch.crawler.inout.messaging.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
