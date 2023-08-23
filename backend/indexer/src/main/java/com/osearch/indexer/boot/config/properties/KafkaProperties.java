package com.osearch.indexer.boot.config.properties;

import com.osearch.indexer.adapter.in.InMessagingProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "inMessagingProperties")
public class KafkaProperties implements InMessagingProperties {

    @Getter
    @Value("${kafka.url}")
    private String url;

    @Value("${kafka.groupId}")
    private String groupId;

    @Value("${kafka.topic}")
    private String topic;

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }
}
