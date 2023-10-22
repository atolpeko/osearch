package com.osearch.crawler.boot.config.properties;

import com.osearch.crawler.application.properties.ApplicationProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceProperties implements ApplicationProperties {

    @Getter
    @Value("${service.externalUrlRegex}")
    private String externalUrlRegex;

    @Getter
    @Value("${service.invalidUrlRegex}")
    private String invalidUrlRegex;

    @Value("${service.crawlerThreadsCount}")
    private Integer crawlerThreadsCount;

    @Value("${service.processorThreadsCount}")
    private Integer processorThreadsCount;

    @Value("${service.pagesToKeep}")
    private Integer pagesToKeep;

    @Override
    public int getPagesToKeepCount() {
        return pagesToKeep;
    }

    @Override
    public int getCrawlerThreadsCount() {
        return crawlerThreadsCount;
    }

    @Override
    public int getProcessorThreadsCount() {
        return processorThreadsCount;
    }
}
