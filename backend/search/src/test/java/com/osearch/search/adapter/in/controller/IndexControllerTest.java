package com.osearch.search.adapter.in.controller;

import static com.osearch.search.fixture.IndexControllerFixture.INDEX_1;
import static com.osearch.search.fixture.IndexControllerFixture.INDEX_1_COUNT;
import static com.osearch.search.fixture.IndexControllerFixture.INDEX_2;
import static com.osearch.search.fixture.IndexControllerFixture.INDEX_2_COUNT;
import static com.osearch.search.fixture.IndexControllerFixture.LIMIT;
import static com.osearch.search.fixture.IndexControllerFixture.NEXT_OFFSET;
import static com.osearch.search.fixture.IndexControllerFixture.OFFSET;
import static com.osearch.search.fixture.IndexControllerFixture.URL;

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
class IndexControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldReturnIndexesInfo() throws Exception {
        mvc.perform(get(URL)
                .param("offset", String.valueOf(OFFSET))
                .param("limit", String.valueOf(LIMIT)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath(INDEX_1).value(INDEX_1_COUNT))
            .andExpect(jsonPath(INDEX_2).value(INDEX_2_COUNT))
            .andExpect(jsonPath("$.nextOffset").value(NEXT_OFFSET))
            .andExpect(jsonPath("$.lastOffset").value(OFFSET))
            .andExpect(jsonPath("$.lastLimit").value(LIMIT));
    }
}