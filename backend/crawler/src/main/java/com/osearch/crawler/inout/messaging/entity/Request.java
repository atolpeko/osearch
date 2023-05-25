package com.osearch.crawler.inout.messaging.entity;

import java.util.List;
import lombok.Data;

@Data
public class Request {

    public enum Operation { START, STOP }

    private Operation operation;
    private List<String> urls;
}
