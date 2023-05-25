package com.osearch.crawler.config.properties;

import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "service")
public class ServiceProperties {

    @Getter
    private static Integer crawlerThreadsCount;

    @Getter
    private static Integer processorThreadsCount;

    void setCrawlerThreadsCount(Integer crawlerThreadsCount) {
        ServiceProperties.crawlerThreadsCount = crawlerThreadsCount;
    }

    void setProcessorThreadsCount(Integer processorThreadsCount) {
        ServiceProperties.processorThreadsCount = processorThreadsCount;
    }
}
