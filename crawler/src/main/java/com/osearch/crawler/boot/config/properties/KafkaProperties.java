package com.osearch.crawler.boot.config.properties;

import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.adapter.out.messaging.properties.OutMessagingProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "messageProperties")
public class KafkaProperties implements InMessagingProperties, OutMessagingProperties {

    @Getter
    @Value("${kafka.url}")
    private String url;

    @Getter
    @Value("${kafka.maxMessageSize}")
    private Integer maxMessageSize;

    @Value("${kafka.groupId}")
    private String groupId;

    @Value("${kafka.pageTopic}")
    private String pageTopic;

    @Value("${kafka.requestTopic}")
    private String requestTopic;

    @Value("${kafka.responseTopic}")
    private String responseTopic;

    @Override
    public String getTopic() {
        return pageTopic;
    }

    @Override
    public String getResponseTopic() {
        return responseTopic;
    }

    @Override
    public String getRequestTopic() {
        return requestTopic;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }
}
