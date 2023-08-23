package com.osearch.crawler.boot.config;

import com.osearch.crawler.boot.config.properties.RestProperties;
import com.osearch.crawler.boot.config.rest.OutRequestLoggingInterceptor;
import com.osearch.crawler.boot.config.rest.UserAgentInterceptor;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(
        RestProperties restProperties,
        UserAgentInterceptor userAgentInterceptor,
        OutRequestLoggingInterceptor requestLoggingInterceptor
    ) {
        var timeout = Duration.ofMillis(restProperties.getTimeout());
        return new RestTemplateBuilder()
            .setConnectTimeout(timeout)
            .setReadTimeout(timeout)
            .interceptors(userAgentInterceptor, requestLoggingInterceptor)
            .build();
    }

    @Bean
    public UserAgentInterceptor userAgentInterceptor(RestProperties restProperties) {
        var userAgent = restProperties.getUserAgent();
        return new UserAgentInterceptor(userAgent);
    }
}
