package com.osearch.ranker.domain.ranker;

import static com.osearch.ranker.fixture.PageRankRankerFixture.DAMPING_FACTOR;
import static com.osearch.ranker.fixture.PageRankRankerFixture.INDEX;
import static com.osearch.ranker.fixture.PageRankRankerFixture.ITERATIONS;
import static com.osearch.ranker.fixture.PageRankRankerFixture.PAGE_1;
import static com.osearch.ranker.fixture.PageRankRankerFixture.PAGE_2;
import static com.osearch.ranker.fixture.PageRankRankerFixture.PAGE_3;
import static com.osearch.ranker.fixture.PageRankRankerFixture.PAGE_4;

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
class PageRankRankerTest {

    @InjectMocks
    PageRankRanker target;

    @Mock
    DomainProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getPageRankIterations()).thenReturn(ITERATIONS);
        when(properties.getPageRankDampingFactor()).thenReturn(DAMPING_FACTOR);
    }

    @Test
    void shouldRank() {
        target.rank(INDEX);

        assertEquals(PAGE_1.getPageRank(),
            Double.max(PAGE_1.getPageRank(), PAGE_2.getPageRank()));
        assertEquals(PAGE_2.getPageRank(),
            Double.max(PAGE_2.getPageRank(), PAGE_3.getPageRank()));
        assertEquals(PAGE_3.getPageRank(),
            Double.max(PAGE_3.getPageRank(), PAGE_4.getPageRank()));
    }
}