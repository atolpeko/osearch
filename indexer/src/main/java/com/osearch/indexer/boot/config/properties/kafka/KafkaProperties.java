package com.osearch.indexer.boot.config.properties.kafka;

import com.osearch.indexer.adapter.in.messaging.InMessagingProperties;
import com.osearch.indexer.adapter.out.messaging.properties.OutMessagingProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "messagingProperties")
public class KafkaProperties implements InMessagingProperties, OutMessagingProperties {

    @Getter
    private final String brokers;

    @Value("${kafka.in.groupId}")
    private String groupId;

    @Value("${kafka.in.topic}")
    private String inTopic;

    @Value("${kafka.out.topic}")
    private String outTopic;

    @Autowired
    public KafkaProperties(KafkaBrokers brokers) {
        this.brokers = String.join(",", brokers.getBrokers());
    }

    @Override
    public String getTopic() {
        return inTopic;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getOutTopic() {
        return outTopic;
    }
}
