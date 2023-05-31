package com.osearch.indexer.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceProperties {

    @Value("${service.threadsCount}")
    private Integer threadsCount;

    @Value("${service.waitBeforeRequests}")
    private Integer waitBeforeRequests;
}
