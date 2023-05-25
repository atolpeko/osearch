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
    private final List<URLDto> urlDtos
            = new ArrayList<>(KafkaProperties.getBulkMessagesCount());

    public URLMessageSenderImpl(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper mapper
    ) {
        super(kafkaTemplate, mapper);
    }

    @Override
    public synchronized void send(URLDto dto) {
        int messages = KafkaProperties.getBulkMessagesCount();
        if (urlDtos.size() < messages) {
            log.debug("Preparing bulk send. {} messages left",
                    messages - urlDtos.size() - 1);
            urlDtos.add(dto);
        } else {
            log.debug("Bulk send. {} messages", urlDtos.size());
            produce(KafkaProperties.getUrlTopic(), urlDtos);
            urlDtos.clear();
        }
    }
}