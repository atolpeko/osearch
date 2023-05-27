package com.osearch.crawler.inout.messaging.entity;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    public enum Operation { START, STOP }

    private Operation operation;
    private List<String> urls;
}
