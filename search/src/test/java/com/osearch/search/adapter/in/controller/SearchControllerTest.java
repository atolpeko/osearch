package com.osearch.search.adapter.in.controller;

import static com.osearch.search.fixture.SearchControllerFixture.LIMIT;
import static com.osearch.search.fixture.SearchControllerFixture.NEXT_OFFSET;
import static com.osearch.search.fixture.SearchControllerFixture.OFFSET;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_1_TITLE;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_1_TITLE_VALUE;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_1_URL;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_1_URL_VALUE;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_2_TITLE;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_2_TITLE_VALUE;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_2_URL;
import static com.osearch.search.fixture.SearchControllerFixture.PAGE_2_URL_VALUE;
import static com.osearch.search.fixture.SearchControllerFixture.SEARCH_STRING;
import static com.osearch.search.fixture.SearchControllerFixture.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osearch.search.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@Tag("category.IntegrationTest")
class SearchControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturnPages() throws Exception {
        mvc.perform(get(URL)
                .param("searchString", SEARCH_STRING)
                .param("offset", String.valueOf(OFFSET))
                .param("limit", String.valueOf(LIMIT)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(PAGE_1_URL).value(PAGE_1_URL_VALUE))
            .andExpect(jsonPath(PAGE_2_URL).value(PAGE_2_URL_VALUE))
            .andExpect(jsonPath(PAGE_1_TITLE).value(PAGE_1_TITLE_VALUE))
            .andExpect(jsonPath(PAGE_2_TITLE).value(PAGE_2_TITLE_VALUE))
            .andExpect(jsonPath("$.nextOffset").value(NEXT_OFFSET))
            .andExpect(jsonPath("$.lastOffset").value(OFFSET))
            .andExpect(jsonPath("$.lastLimit").value(LIMIT));
    }
}