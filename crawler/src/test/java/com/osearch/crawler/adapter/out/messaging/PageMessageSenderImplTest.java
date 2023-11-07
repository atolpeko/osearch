package com.osearch.crawler.adapter.out.messaging;

import static com.osearch.crawler.fixture.PageMessageSenderFixture.TOPIC;
import static com.osearch.crawler.fixture.PageMessageSenderFixture.PAGE;
import static com.osearch.crawler.fixture.PageMessageSenderFixture.PAGE_DTO;
import static com.osearch.crawler.fixture.PageMessageSenderFixture.pageJson;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.out.messaging.mapper.PageMapper;
import com.osearch.crawler.adapter.out.messaging.properties.OutMessagingProperties;

import com.osearch.crawler.application.port.exception.MessagingException;
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
    PageMapper mapper;

    @Mock
    OutMessagingProperties properties;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(properties.getTopic()).thenReturn(TOPIC);
        when(objectMapper.writeValueAsString(PAGE_DTO)).thenReturn(pageJson());
    }

    @Test
    void shouldSendPage() {
        when(mapper.toDto(PAGE)).thenReturn(PAGE_DTO);

        target.send(PAGE);
        verify(kafkaTemplate, times(1)).send(TOPIC, pageJson());
    }

    @Test
    void shouldThrowMessagingException() {
        when(mapper.toDto(PAGE)).thenReturn(PAGE_DTO);
        when(kafkaTemplate.send(TOPIC, pageJson())).thenThrow(RuntimeException.class);

        assertThrows(MessagingException.class, () -> target.send(PAGE));
    }
}