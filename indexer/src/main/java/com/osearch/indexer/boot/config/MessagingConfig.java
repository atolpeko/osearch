package com.osearch.indexer.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.adapter.out.messaging.PageMessageSenderImpl;
import com.osearch.indexer.adapter.out.messaging.mapper.PageMapper;
import com.osearch.indexer.adapter.out.messaging.properties.OutMessagingProperties;
import com.osearch.indexer.application.port.PageMessageSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class MessagingConfig {

    @Bean
    public PageMessageSender pageMessageSender(
        OutMessagingProperties properties,
        KafkaTemplate<String, String> kafkaTemplate,
        PageMapper mapper,
        ObjectMapper objectMapper
    ) {
        return new PageMessageSenderImpl(properties, kafkaTemplate, mapper, objectMapper);
    }
}
