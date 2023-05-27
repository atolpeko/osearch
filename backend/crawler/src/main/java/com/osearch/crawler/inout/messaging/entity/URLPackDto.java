package com.osearch.crawler.inout.messaging.entity;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class URLPackDto {
    private final List<URLDto> urls;
}
