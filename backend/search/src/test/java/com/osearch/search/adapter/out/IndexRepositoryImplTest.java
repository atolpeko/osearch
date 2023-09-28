package com.osearch.search.adapter.out;

import static com.osearch.search.fixture.IndexRepositoryFixture.INFO;
import static com.osearch.search.fixture.IndexRepositoryFixture.LIMIT;
import static com.osearch.search.fixture.IndexRepositoryFixture.OFFSET;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.osearch.search.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@Tag("category.IntegrationTest")
class IndexRepositoryImplTest {

    @Autowired
    IndexRepositoryImpl repository;

    @Test
    void shouldExtractIndexesInfo() {
        var info = repository.findInfo(OFFSET, LIMIT);
        assertEquals(INFO, info);
    }
}