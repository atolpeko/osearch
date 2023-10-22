package com.osearch.search.adapter.in.mapper;

import static com.osearch.search.fixture.IndexInfoMapperTest.RESULT;
import static com.osearch.search.fixture.IndexInfoMapperTest.SOURCE;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class IndexInfoMapperTest {
    IndexInfoMapper target;

    @BeforeEach
    void setUp() {
        target = new IndexInfoMapper();
    }

    @Test
    void shouldMap() {
        var result = target.map(SOURCE);
        assertEquals(RESULT, result);
    }
}