package com.osearch.crawler.inout.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.config.properties.KafkaProperties;
import com.osearch.crawler.inout.messaging.entity.URLDto;

import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class URLMessageSenderImpl extends BaseMessageProducer implements URLMessageSender {
    private final KafkaProperties properties;

    public URLMessageSenderImpl(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper mapper,
            KafkaProperties properties
    ) {
        super(kafkaTemplate, mapper);
        this.properties = properties;
    }

    @Override
    public synchronized void send(URLDto dto) {
        produce(properties.getUrlTopic(), dto);
    }
}