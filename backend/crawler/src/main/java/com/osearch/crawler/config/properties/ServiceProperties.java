package com.osearch.crawler.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceProperties {

    @Value("${service.crawlerThreadsCount}")
    private Integer crawlerThreadsCount;

    @Value("${service.processorThreadsCount}")
    private Integer processorThreadsCount;

    @Value("${service.urlsToKeep}")
    private Integer urlsToKeep;
}
