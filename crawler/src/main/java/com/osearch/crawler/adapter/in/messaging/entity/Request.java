package com.osearch.crawler.adapter.in.messaging.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a request object used to interact with crawler service via Kafka.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {

    /**
     * Operation class represents a set of possible operations
     * that can be performed on crawler service.
     */
    public enum Operation { START, STOP }

    private Operation operation;
    private List<String> urls;
}
