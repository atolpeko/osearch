package com.osearch.crawler.config;

import com.osearch.crawler.config.properties.RestProperties;
import com.osearch.crawler.inout.web.util.OutRequestLoggingInterceptor;
import com.osearch.crawler.inout.web.util.UserAgentInterceptor;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestProperties restProperties;

    @Bean
    public RestTemplate restTemplate(
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
    public UserAgentInterceptor userAgentInterceptor() {
        var userAgent = restProperties.getUserAgent();
        return new UserAgentInterceptor(userAgent);
    }
}
