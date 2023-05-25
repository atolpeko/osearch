package com.osearch.crawler.inout.messaging.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Log4j2
public abstract class BaseMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    protected void produce(String topic, Object object) {
        var data = object.toString();
        log.debug("Sending to kafka topic {}: {}", topic, data);
        kafkaTemplate.send(topic, data);
    }
}
