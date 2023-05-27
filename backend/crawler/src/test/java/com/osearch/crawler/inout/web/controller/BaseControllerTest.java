package com.osearch.crawler.inout.web.controller;

import static com.osearch.crawler.fixture.BaseControllerFixture.REDIRECT_URL;
import static com.osearch.crawler.fixture.BaseControllerFixture.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osearch.crawler.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@Tag("category.IntegrationTest")
@IntegrationTest
@AutoConfigureMockMvc
class BaseControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldRedirectToSwagger() throws Exception {
        mvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_URL));
    }
}