package com.osearch.crawler.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaProperties {

    @Value("${kafka.url}")
    private String url;

    @Value("${kafka.groupId}")
    private String groupId;

    @Value("${kafka.urlTopic}")
    private String urlTopic;

    @Value("${kafka.requestTopic}")
    private String requestTopic;

    @Value("${kafka.responseTopic}")
    private String responseTopic;

    @Value("${kafka.maxMessageSize}")
    private Integer maxMessageSize;
}
