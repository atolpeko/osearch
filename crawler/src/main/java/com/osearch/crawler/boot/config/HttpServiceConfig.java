package com.osearch.crawler.boot.config;

import com.osearch.crawler.adapter.out.http.HttpServiceImpl;
import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.boot.config.properties.RestProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpServiceConfig {

    @Bean
    public HttpService httpService(
        RestTemplate restTemplate,
        RestProperties restProperties
    ) {
        return new HttpServiceImpl(restTemplate, restProperties);
    }
}
