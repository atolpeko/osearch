package com.osearch.ranker.boot.config.properties;

import com.osearch.ranker.adapter.in.InMessagingProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "messagingProperties")
public class KafkaProperties implements InMessagingProperties {

    @Getter
    @Value("${kafka.url}")
    private String url;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.groupId}")
    private String groupId;

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }
}
