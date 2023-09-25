package com.osearch.ranker.domain.service;

import static com.osearch.ranker.fixture.TopicRankerFixture.INDEX;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_1;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_1_RANK;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_2;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_2_RANK;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_3;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_3_RANK;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_4;
import static com.osearch.ranker.fixture.TopicRankerFixture.PAGE_4_RANK;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class TopicRankerTest {
    TopicRanker target;

    @BeforeEach
    void setUp() {
        target = new TopicRanker();
    }

    @Test
    void shouldRank() {
        target.rank(INDEX);

        assertEquals(PAGE_1_RANK, PAGE_1.getTopicRank());
        assertEquals(PAGE_2_RANK, PAGE_2.getTopicRank());
        assertEquals(PAGE_3_RANK, PAGE_3.getTopicRank());
        assertEquals(PAGE_4_RANK, PAGE_4.getTopicRank());
    }
}