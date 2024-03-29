package com.osearch.ranker.application.usecase;

import static com.osearch.ranker.fixture.RankerUseCaseFixture.TOPIC_NAMES;
import static com.osearch.ranker.fixture.RankerUseCaseFixture.NEW_PAGE_TOTAL_RANK;
import static com.osearch.ranker.fixture.RankerUseCaseFixture.PAGE;
import static com.osearch.ranker.fixture.RankerUseCaseFixture.PAGE_ID;
import static com.osearch.ranker.fixture.RankerUseCaseFixture.newIndexes;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.domain.ranker.Ranker;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class RankerUseCaseImplTest {

    @InjectMocks
    RankerUseCaseImpl target;

    @Mock
    Ranker ranker;

    @Mock
    PageRepository pageRepository;

    @Mock
    IndexRepository indexRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(pageRepository.findById(PAGE_ID)).thenReturn(Optional.of(PAGE));
    }

    @Test
    void shouldSaveNewIndexes() {
        TOPIC_NAMES.forEach(topic ->
            when(indexRepository.findByTopic(topic)).thenReturn(Optional.empty()));
        newIndexes().forEach(index ->
            doAnswer(invocation -> {
                index.getPages().forEach(page -> page.setTotalRank(NEW_PAGE_TOTAL_RANK));
                return null;
            }).when(ranker).rank(index));

        target.process(PAGE_ID);
        newIndexes().forEach(index ->
            verify(indexRepository, times(1)).save(index));
    }
}