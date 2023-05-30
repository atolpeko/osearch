package com.osearch.crawler.inout.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Log4j2
public abstract class BaseMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    protected void produce(String topic, Object object) {
        try {
            log.debug("Sending to kafka topic {}: {}", topic, object.toString());
            var data = objectMapper.writeValueAsString(object);
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.error("Cannot send message to kafka topic {}: {}",
                    topic, e.getMessage());
        }
    }
}
