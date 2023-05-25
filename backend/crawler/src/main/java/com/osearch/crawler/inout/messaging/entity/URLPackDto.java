package com.osearch.crawler.inout.messaging.entity;

import java.util.List;
import lombok.Data;

@Data
public class URLPackDto {
    private List<URLDto> urls;
}
