package com.osearch.indexer.adapter.out.messaging;

import static com.osearch.indexer.fixture.PageMessageSenderFixture.ID;
import static com.osearch.indexer.fixture.PageMessageSenderFixture.JSON;
import static com.osearch.indexer.fixture.PageMessageSenderFixture.TOPIC;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.application.exception.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.kafka.core.KafkaTemplate;

@Tag("category.UnitTest")
class PageMessageSenderImplTest {

    @InjectMocks
    PageMessageSenderImpl target;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    OutMessagingProperties properties;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(properties.getOutTopic()).thenReturn(TOPIC);
        when(objectMapper.writeValueAsString(ID)).thenReturn(JSON);
    }

    @Test
    void shouldSendPage() {
        target.send(ID);
        verify(kafkaTemplate, times(1)).send(TOPIC, JSON);
    }

    @Test
    void shouldThrowMessagingException() {
        when(kafkaTemplate.send(TOPIC, JSON)).thenThrow(RuntimeException.class);
        assertThrows(MessagingException.class, () -> target.send(ID));
    }

}