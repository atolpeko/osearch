package com.osearch.indexer.boot.config.properties;

import com.osearch.indexer.adapter.in.messaging.InMessagingProperties;
import com.osearch.indexer.adapter.out.messaging.OutMessagingProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "messagingProperties")
public class KafkaProperties implements InMessagingProperties, OutMessagingProperties {

    @Getter
    @Value("${kafka.url}")
    private String url;

    @Value("${kafka.in.groupId}")
    private String groupId;

    @Value("${kafka.in.topic}")
    private String inTopic;

    @Value("${kafka.out.topic}")
    private String outTopic;

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
