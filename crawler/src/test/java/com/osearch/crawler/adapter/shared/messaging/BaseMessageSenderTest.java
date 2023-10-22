package com.osearch.crawler.adapter.shared.messaging;

import static com.osearch.crawler.fixture.BaseMessageSenderFixture.JSON;
import static com.osearch.crawler.fixture.BaseMessageSenderFixture.OBJECT;
import static com.osearch.crawler.fixture.BaseMessageSenderFixture.TOPIC;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

@Tag("category.UnitTest")
class BaseMessageSenderTest {

    @InjectMocks
    BaseMessageSender target;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSend() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(OBJECT)).thenReturn(JSON);

        target.send(TOPIC, OBJECT);
        verify(kafkaTemplate, times(1)).send(TOPIC, JSON);
    }
}