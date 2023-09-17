package com.osearch.ranker.domain.service.ranker;

import static com.osearch.ranker.fixture.RankerServiceFixture.INDEX;
import static com.osearch.ranker.fixture.RankerServiceFixture.KEYWORD_RANK_WEIGHT;
import static com.osearch.ranker.fixture.RankerServiceFixture.META_RANK_WEIGHT;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_1;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_1_KEYWORD_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_1_META_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_1_PAGE_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_1_TOTAL_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_2;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_2_KEYWORD_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_2_META_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_2_PAGE_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_2_TOTAL_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_3;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_3_KEYWORD_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_3_META_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_3_PAGE_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_3_TOTAL_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_4;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_4_KEYWORD_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_4_META_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_4_PAGE_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_4_TOTAL_RANK;
import static com.osearch.ranker.fixture.RankerServiceFixture.PAGE_RANK_WEIGHT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import com.osearch.ranker.domain.properties.DomainProperties;
import com.osearch.ranker.domain.service.ranker.impl.Ranker;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class RankerServiceImplTest {
    RankerServiceImpl target;

    @Mock
    Ranker keywordRanker;

    @Mock
    Ranker pageRanRanker;

    @Mock
    Ranker metaRanker;

    @Mock
    DomainProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getKeywordRankWeight()).thenReturn(KEYWORD_RANK_WEIGHT);
        when(properties.getPageRankWeight()).thenReturn(PAGE_RANK_WEIGHT);
        when(properties.getMetaRankWeight()).thenReturn(META_RANK_WEIGHT);

        doAnswer(invocation -> {
            PAGE_1.setKeywordRank(PAGE_1_KEYWORD_RANK);
            PAGE_2.setKeywordRank(PAGE_2_KEYWORD_RANK);
            PAGE_3.setKeywordRank(PAGE_3_KEYWORD_RANK);
            PAGE_4.setKeywordRank(PAGE_4_KEYWORD_RANK);
            return null;
        } ).when(keywordRanker).rank(INDEX);

        doAnswer(invocation -> {
            PAGE_1.setPageRank(PAGE_1_PAGE_RANK);
            PAGE_2.setPageRank(PAGE_2_PAGE_RANK);
            PAGE_3.setPageRank(PAGE_3_PAGE_RANK);
            PAGE_4.setPageRank(PAGE_4_PAGE_RANK);
            return null;
        } ).when(pageRanRanker).rank(INDEX);

        doAnswer(invocation -> {
            PAGE_1.setMetaRank(PAGE_1_META_RANK);
            PAGE_2.setMetaRank(PAGE_2_META_RANK);
            PAGE_3.setMetaRank(PAGE_3_META_RANK);
            PAGE_4.setMetaRank(PAGE_4_META_RANK);
            return null;
        } ).when(metaRanker).rank(INDEX);

        target = new RankerServiceImpl(
          List.of(keywordRanker, metaRanker, pageRanRanker),
          properties
        );
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