package com.osearch.crawler.adapter.in.messaging;

import static com.osearch.crawler.fixture.ResponseMessageSenderFixture.TOPIC;
import static com.osearch.crawler.fixture.ResponseMessageSenderFixture.response;
import static com.osearch.crawler.fixture.ResponseMessageSenderFixture.responseJson;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.kafka.core.KafkaTemplate;

@Tag("category.UnitTest")
class ResponseMessageSenderTest {

    @InjectMocks
    ResponseMessageSender target;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    InMessagingProperties properties;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(properties.getResponseTopic()).thenReturn(TOPIC);
        when(objectMapper.writeValueAsString(response())).thenReturn(responseJson());
    }

    @Test
    void shouldSend() {
        target.send(response());
        verify(kafkaTemplate, times(1)).send(TOPIC, responseJson());
    }
}