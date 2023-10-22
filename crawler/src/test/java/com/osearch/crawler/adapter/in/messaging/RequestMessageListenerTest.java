package com.osearch.crawler.adapter.in.messaging;

import static com.osearch.crawler.fixture.RequestMessageListenerFixture.CRAWLER_ALREADY_RINNING_RESPONSE;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.CRAWLER_NOT_RINNING_RESPONSE;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.INITIAL_URLS;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.INVALID_STOP_REQUEST_JSON;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.INVALID_START_REQUEST_JSON;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.INVALID_START_RESPONSE;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.INVALID_STOP_RESPONSE;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.START_REQUEST;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.STOP_REQUEST;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.STOP_REQUEST_JSON;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.SUCCESSFUL_RESPONSE;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.TOPIC;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.startRequestJson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.crawler.adapter.in.messaging.mapper.RequestMapper;
import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.CrawlerUseCase;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Log4j2
@Tag("category.UnitTest")
class RequestMessageListenerTest {

    @InjectMocks
    RequestMessageListener target;

    @Mock
    CrawlerUseCase useCase;

    @Mock
    ResponseMessageSender messageSender;

    @Mock
    RequestMapper mapper;

    @Mock
    InMessagingProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getRequestTopic()).thenReturn(TOPIC);
        when(mapper.toRequest(startRequestJson())).thenReturn(START_REQUEST);
        when(mapper.toRequest(STOP_REQUEST_JSON)).thenReturn(STOP_REQUEST);
        when(mapper.toRequest(INVALID_START_REQUEST_JSON))
            .thenThrow(new IllegalArgumentException("INVALID_START"));
        when(mapper.toRequest(INVALID_STOP_REQUEST_JSON))
            .thenThrow(new IllegalArgumentException("INVALID_STOP"));
    }

    @Test
    void shouldStartCrawlerWhenRequestIsValid() {
        doAnswer(i -> when(useCase.isRunning()).thenReturn(true))
            .when(useCase).start(INITIAL_URLS);

        target.listen(startRequestJson());
        assertTrue(useCase.isRunning());
    }

    @Test
    void shouldSendResponseWhenStartsCrawler() {
        target.listen(startRequestJson());
        verify(messageSender, times(1)).send(SUCCESSFUL_RESPONSE);
    }

    @Test
    void shouldStopCrawlerWhenRequestIsValid() {
        doAnswer(i -> when(useCase.isRunning()).thenReturn(true))
            .when(useCase).start(INITIAL_URLS);
        doAnswer(i -> when(useCase.isRunning()).thenReturn(false))
            .when(useCase).stop();

        useCase.start(INITIAL_URLS);
        target.listen(STOP_REQUEST_JSON);

        assertFalse(useCase.isRunning());
    }

    @Test
    void shouldSendResponseWhenStopsCrawler() {
        useCase.start(INITIAL_URLS);
        target.listen(STOP_REQUEST_JSON);

        verify(messageSender, times(1)).send(SUCCESSFUL_RESPONSE);
    }

    @Test
    void shouldSendResponseWhenStartRequestIsInvalid() {
        target.listen(INVALID_START_REQUEST_JSON);
        verify(messageSender, times(1)).send(INVALID_START_RESPONSE);
    }

    @Test
    void shouldSendResponseWhenStopRequestIsInvalid() {
        target.listen(INVALID_STOP_REQUEST_JSON);
        verify(messageSender, times(1)).send(INVALID_STOP_RESPONSE);
    }

    @Test
    void shouldSendResponseWhenCrawlerAlreadyRunning() {
        doThrow(CrawlerAlreadyRunningException.class).when(useCase).start(INITIAL_URLS);

        target.listen(startRequestJson());
        verify(messageSender, times(1)).send(CRAWLER_ALREADY_RINNING_RESPONSE);
    }

    @Test
    void shouldSendResponseWhenCrawlerNotRunning() {
        doThrow(CrawlerNotRunningException.class).when(useCase).stop();

        target.listen(STOP_REQUEST_JSON);
        verify(messageSender, times(1)).send(CRAWLER_NOT_RINNING_RESPONSE);
    }
}