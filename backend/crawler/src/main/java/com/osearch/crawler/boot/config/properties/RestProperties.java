package com.osearch.crawler.boot.config.properties;

import com.osearch.crawler.adapter.out.http.HttpProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestProperties implements HttpProperties {

    @Getter
    @Value("${rest.timeout}")
    private Integer timeout;

    @Getter
    @Value("${rest.userAgent}")
    private String userAgent;

    @Value("${rest.maxRedirects}")
    private Integer maxRedirects;

    @Override
    public int getMaxRedirects() {
        return maxRedirects;
    }
}
