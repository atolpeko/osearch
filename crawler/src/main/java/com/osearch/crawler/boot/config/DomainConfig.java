package com.osearch.crawler.boot.config;

import com.osearch.crawler.boot.config.properties.ServiceProperties;
import com.osearch.crawler.domain.service.executor.BackgroundExecutor;
import com.osearch.crawler.domain.service.executor.BackgroundExecutorImpl;
import com.osearch.crawler.domain.service.hasher.Hasher;
import com.osearch.crawler.domain.service.hasher.SHA256Hasher;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessorImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public HtmlProcessor htmlProcessor(ServiceProperties properties) {
        return new HtmlProcessorImpl(
            properties.getExternalUrlRegex(),
            properties.getInvalidUrlRegex()
        );
    }

    @Bean
    public Hasher hasher() {
        return new SHA256Hasher();
    }

    @Bean
    public BackgroundExecutor backgroundExecutor() {
        return new BackgroundExecutorImpl();
    }
}
