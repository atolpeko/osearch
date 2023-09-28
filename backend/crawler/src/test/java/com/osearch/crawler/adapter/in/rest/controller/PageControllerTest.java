package com.osearch.crawler.adapter.in.rest.controller;

import static com.osearch.crawler.fixture.PageControllerFixture.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osearch.crawler.application.usecase.PageUseCase;
import com.osearch.crawler.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@Tag("category.IntegrationTest")
class PageControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PageUseCase useCase;

    @Test
    void shouldReturnUrlsCount() throws Exception {
        var count = useCase.countCrawled();
        mvc.perform(get(URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.count").value(count));
    }
}