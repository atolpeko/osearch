package com.osearch.crawler.inout.messaging.producer;

import com.osearch.crawler.config.properties.KafkaProperties;
import com.osearch.crawler.inout.messaging.entity.URLDto;

import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class URLMessageSenderImpl extends BaseMessageProducer implements URLMessageSender {

    public URLMessageSenderImpl(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void send(URLDto dto) {
        produce(KafkaProperties.getUrlTopic(), dto);
    }
}