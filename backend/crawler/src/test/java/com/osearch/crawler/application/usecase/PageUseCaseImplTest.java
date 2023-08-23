package com.osearch.crawler.application.usecase;

import static com.osearch.crawler.fixture.PageUseCaseFixture.COUNT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.usecase.exception.PageServiceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class PageUseCaseImplTest {

    @InjectMocks
    PageUseCaseImpl target;

    @Mock
    PageRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnCount() {
        when(repository.count()).thenReturn(COUNT);

        var count = target.countProcessed();
        assertEquals(COUNT, count);
    }

    @Test
    void shouldThrowExceptionWhenErrorHappens() {
        doThrow(RuntimeException.class).when(repository).count();
        assertThrows(PageServiceException.class, () -> target.countProcessed());
    }
}