package com.osearch.ranker.boot.config.properties.kafka;

import com.osearch.ranker.adapter.in.properties.InMessagingProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "messagingProperties")
public class KafkaProperties implements InMessagingProperties {

    @Getter
    private final String brokers;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.groupId}")
    private String groupId;

    @Autowired
    public KafkaProperties(KafkaBrokers brokers) {
        this.brokers = String.join(",", brokers.getBrokers());
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }
}
