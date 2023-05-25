package com.osearch.crawler.inout.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.config.properties.KafkaProperties;
import com.osearch.crawler.inout.messaging.entity.Response;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ResponseMessageSenderImpl
        extends BaseMessageProducer implements ResponseMessageSender {

    @Autowired
    public ResponseMessageSenderImpl(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper mapper
    ) {
        super(kafkaTemplate, mapper);
    }

    public void send(Response response) {
        produce(KafkaProperties.getResponseTopic(), response);
    }
}