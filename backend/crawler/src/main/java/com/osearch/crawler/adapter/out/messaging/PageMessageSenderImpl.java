package com.osearch.crawler.adapter.out.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.out.messaging.properties.OutMessagingProperties;
import com.osearch.crawler.adapter.out.messaging.mapper.PageMapper;
import com.osearch.crawler.adapter.shared.messaging.BaseMessageSender;
import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.domain.entity.Page;

import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
public class PageMessageSenderImpl extends BaseMessageSender implements PageMessageSender {
    private final PageMapper mapper;
    private final OutMessagingProperties properties;

    /**
     * Creates a new instance of the PageMessageSenderImpl class.
     *
     * @param kafkaTemplate - The KafkaTemplate used for sending Kafka messages.
     * @param objectMapper - The ObjectMapper used for serializing/deserializing messages.
     * @param mapper - The PageMapper used for mapping Page objects to Kafka messages.
     * @param properties - The OutMessagingProperties used for obtaining messaging configuration.
     */
    public PageMessageSenderImpl(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper,
        PageMapper mapper,
        OutMessagingProperties properties
    ) {
        super(kafkaTemplate, objectMapper);
        this.mapper = mapper;
        this.properties = properties;
    }

    @Override
    public synchronized void send(Page page) {
        var topic = properties.getTopic();
        var dto = mapper.toDto(page);
        super.send(topic, dto);
    }
}