package com.osearch.crawler.inout.messaging.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class URLDto {
    private final String value;

    @Override
    public String toString() {
        return "{ url: " + value + " }";
    }
}
