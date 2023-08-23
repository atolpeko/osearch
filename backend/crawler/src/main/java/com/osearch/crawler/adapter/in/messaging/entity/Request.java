package com.osearch.crawler.adapter.in.messaging.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request implements Serializable {
    public enum Operation { START, STOP }

    private Operation operation;
    private List<String> urls;
}
