package com.osearch.ranker.domain.service.ranker.impl;

import static com.osearch.ranker.fixture.MetaRankerFixture.INDEX;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_1;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_1_RANK;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_2;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_2_RANK;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_3;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_3_RANK;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_4;
import static com.osearch.ranker.fixture.MetaRankerFixture.PAGE_4_RANK;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class MetaRankerTest {
    MetaRanker target;

    @BeforeEach
    void setUp() {
        target = new MetaRanker();
    }

    @Test
    void shouldRank() {
        target.rank(INDEX);

        assertEquals(PAGE_1_RANK, PAGE_1.getMetaRank());
        assertEquals(PAGE_2_RANK, PAGE_2.getMetaRank());
        assertEquals(PAGE_3_RANK, PAGE_3.getMetaRank());
        assertEquals(PAGE_4_RANK, PAGE_4.getMetaRank());
    }
}