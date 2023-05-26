package com.osearch.crawler.inout.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.config.properties.KafkaProperties;
import com.osearch.crawler.inout.messaging.entity.URLDto;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class URLMessageSenderImpl extends BaseMessageProducer implements URLMessageSender {
    private final KafkaProperties properties;
    private final List<URLDto> urlDtos;

    public URLMessageSenderImpl(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper mapper,
            KafkaProperties properties
    ) {
        super(kafkaTemplate, mapper);
        this.properties = properties;
        urlDtos = new ArrayList<>(properties.getBulkMessagesCount());
    }

    @Override
    public synchronized void send(URLDto dto) {
        int messages = properties.getBulkMessagesCount();
        if (urlDtos.size() < messages) {
            log.debug("Preparing bulk send. {} messages left",
                    messages - urlDtos.size() - 1);
            urlDtos.add(dto);
        } else {
            log.debug("Bulk send. {} messages", urlDtos.size());
            produce(properties.getUrlTopic(), urlDtos);
            urlDtos.clear();
        }
    }
}