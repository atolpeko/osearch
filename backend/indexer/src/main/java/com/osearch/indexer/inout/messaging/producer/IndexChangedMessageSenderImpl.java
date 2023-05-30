package com.osearch.indexer.inout.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.config.properties.KafkaProperties;
import com.osearch.indexer.inout.messaging.entity.IndexChange;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class IndexChangedMessageSenderImpl implements IndexChangedMessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaProperties properties;

    @Override
    public void send(long pageId) {
        var topic = properties.getRequestTopic();
        try {
            var message = new IndexChange(pageId);
            var data = objectMapper.writeValueAsString(message);
            log.debug("Sending to kafka topic {}: {}", topic, data);
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.error("Cannot send message to kafka topic {}: {}",
                    topic, e.getMessage());
        }
    }
}