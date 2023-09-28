package com.osearch.search.application.usecase;

import static com.osearch.search.fixture.IndexUseCaseFixture.INFO_PLUS_1;
import static com.osearch.search.fixture.IndexUseCaseFixture.LIMIT;
import static com.osearch.search.fixture.IndexUseCaseFixture.OFFSET;
import static com.osearch.search.fixture.IndexUseCaseFixture.PAGEABLE_INFO;
import static com.osearch.search.fixture.IndexUseCaseFixture.LIMIT_PLUS_1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import com.osearch.search.application.port.IndexRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class IndexUseCaseImplTest {

    @InjectMocks
    IndexUseCaseImpl target;

    @Mock
    IndexRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(repository.findInfo(OFFSET, LIMIT_PLUS_1)).thenReturn(INFO_PLUS_1);
    }

    @Test
    void shouldExtractIndexesInfo() {
        var info = target.getInfo(OFFSET, LIMIT);
        assertEquals(PAGEABLE_INFO, info);
    }
}