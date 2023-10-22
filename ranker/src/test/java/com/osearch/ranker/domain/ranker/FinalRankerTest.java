package com.osearch.ranker.domain.ranker;

import static com.osearch.ranker.fixture.FinalRankerFixture.INDEX;
import static com.osearch.ranker.fixture.FinalRankerFixture.TOPIC_RANK_WEIGHT;
import static com.osearch.ranker.fixture.FinalRankerFixture.META_RANK_WEIGHT;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_1;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_1_TOTAL_RANK;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_2;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_2_TOTAL_RANK;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_3;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_3_TOTAL_RANK;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_4;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_4_TOTAL_RANK;
import static com.osearch.ranker.fixture.FinalRankerFixture.PAGE_RANK_WEIGHT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import com.osearch.ranker.domain.properties.DomainProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class FinalRankerTest {

    @InjectMocks
    FinalRanker target;

    @Mock
    DomainProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getTopicRankWeight()).thenReturn(TOPIC_RANK_WEIGHT);
        when(properties.getPageRankWeight()).thenReturn(PAGE_RANK_WEIGHT);
        when(properties.getMetaRankWeight()).thenReturn(META_RANK_WEIGHT);
    }

    @Test
    void shouldRank() {
        target.rank(INDEX);

        assertEquals(PAGE_1_TOTAL_RANK, PAGE_1.getTotalRank());
        assertEquals(PAGE_2_TOTAL_RANK, PAGE_2.getTotalRank());
        assertEquals(PAGE_3_TOTAL_RANK, PAGE_3.getTotalRank());
        assertEquals(PAGE_4_TOTAL_RANK, PAGE_4.getTotalRank());
    }
}