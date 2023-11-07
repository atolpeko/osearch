package com.osearch.crawler.adapter.in.rest.controller;

import static com.osearch.crawler.fixture.CrawlerControllerFixture.INITIAL_URLS;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.IS_RUNNING_URL;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.START_URL;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.STOP_URL;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.startRequestJson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osearch.crawler.application.usecase.CrawlerUseCase;
import com.osearch.crawler.config.IntegrationTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@Tag("category.IntegrationTest")
class CrawlerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CrawlerUseCase useCase;

    @AfterEach
    void cleanUp() {
        if (useCase.isRunning()) {
            useCase.stop();
        }
    }

    @Test
    void shouldStartCrawler() throws Exception {
        mvc.perform(post(START_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(startRequestJson()))
            .andDo(print())
            .andExpect(status().isOk());

        assertTrue(useCase.isRunning());
    }

    @Test
    void shouldNotStartCrawlerWhenItsRunning() throws Exception {
        useCase.start(INITIAL_URLS);
        mvc.perform(post(START_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(startRequestJson()))
            .andDo(print())
            .andExpect(status().isExpectationFailed());

        assertTrue(useCase.isRunning());
    }

    @Test
    void shouldStopCrawler() throws Exception {
        useCase.start(INITIAL_URLS);
        mvc.perform(delete(STOP_URL))
            .andDo(print())
            .andExpect(status().isOk());

        assertFalse(useCase.isRunning());
    }

    @Test
    void shouldNotStopCrawlerWhenItsNotRunning() throws Exception {
        mvc.perform(delete(STOP_URL))
            .andDo(print())
            .andExpect(status().isExpectationFailed());

        assertFalse(useCase.isRunning());
    }

    @Test
    void shouldReturnRunningTrueWhenCrawlerIsRunning() throws Exception {
        useCase.start(INITIAL_URLS);
        mvc.perform(get(IS_RUNNING_URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.isRunning").value(true));
    }

    @Test
    void shouldReturnRunningFalseWhenCrawlerIsNotRunning() throws Exception {
        mvc.perform(get(IS_RUNNING_URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.isRunning").value(false));
    }
}