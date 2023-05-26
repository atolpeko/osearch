package com.osearch.crawler.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RestProperties {

    @Value("${rest.timeout}")
    private Integer timeout;

    @Value("${rest.maxRedirects}")
    private Integer maxRedirects;

    @Value("${rest.userAgent}")
    private String userAgent;
}
