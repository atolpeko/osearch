package com.osearch.ranker.domain.service.indexer;

import static com.osearch.ranker.fixture.IndexerFixture.PAGE;
import static com.osearch.ranker.fixture.IndexerFixture.RESULT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class IndexImplTest {
    IndexImpl target;

    @BeforeEach
    void setUp() {
        target = new IndexImpl();
    }

    @Test
    void shouldIndex() {
        var reult = target.index(PAGE);
        assertEquals(RESULT, reult);
    }
}