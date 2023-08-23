package com.osearch.crawler.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.ResponseMessageSender;
import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.adapter.out.messaging.PageMessageSenderImpl;
import com.osearch.crawler.adapter.out.messaging.mapper.PageMapper;
import com.osearch.crawler.adapter.out.messaging.properties.OutMessagingProperties;
import com.osearch.crawler.adapter.shared.messaging.BaseMessageSender;
import com.osearch.crawler.application.port.PageMessageSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class MessagingConfig {

    @Bean
    public ResponseMessageSender responseMessageSender(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper,
        InMessagingProperties properties
    ) {
        return new ResponseMessageSender(kafkaTemplate, objectMapper, properties);
    }

    @Bean
    public PageMessageSender pageMessageSender(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper,
        PageMapper mapper,
        OutMessagingProperties properties
    ) {
        return new PageMessageSenderImpl(kafkaTemplate, objectMapper, mapper, properties);
    }

    @Bean
    public BaseMessageSender baseMessageSender(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper
    ) {
        return new BaseMessageSender(kafkaTemplate, objectMapper);
    }
}
