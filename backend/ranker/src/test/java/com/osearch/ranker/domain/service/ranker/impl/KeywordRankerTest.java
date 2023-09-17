package com.osearch.ranker.domain.service.ranker.impl;

import static com.osearch.ranker.fixture.KeywordRankerFixture.INDEX;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_1;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_1_RANK;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_2;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_2_RANK;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_3;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_3_RANK;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_4;
import static com.osearch.ranker.fixture.KeywordRankerFixture.PAGE_4_RANK;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class KeywordRankerTest {
    KeywordRanker target;

    @BeforeEach
    void setUp() {
        target = new KeywordRanker();
    }

    @Test
    void shouldRank() {
        target.rank(INDEX);

        assertEquals(PAGE_1_RANK, PAGE_1.getKeywordRank());
        assertEquals(PAGE_2_RANK, PAGE_2.getKeywordRank());
        assertEquals(PAGE_3_RANK, PAGE_3.getKeywordRank());
        assertEquals(PAGE_4_RANK, PAGE_4.getKeywordRank());
    }
}