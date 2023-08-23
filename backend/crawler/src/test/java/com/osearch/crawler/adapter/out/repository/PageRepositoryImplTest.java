package com.osearch.crawler.adapter.out.repository;

import static com.osearch.crawler.fixture.PageRepositoryFixture.PAGES_NUMBER;
import static com.osearch.crawler.fixture.PageRepositoryFixture.URL_HASH;
import static com.osearch.crawler.fixture.PageRepositoryFixture.page;
import static com.osearch.crawler.fixture.PageRepositoryFixture.pageDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.osearch.crawler.adapter.out.repository.jpa.PageDtoJpaRepository;
import com.osearch.crawler.adapter.out.repository.mapper.PageMapper;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class PageRepositoryImplTest {

    @InjectMocks
    PageRepositoryImpl target;

    @Mock
    PageDtoJpaRepository jpaRepository;

    @Mock
    PageMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mapper.toDto(page())).thenReturn(pageDto());
        when(mapper.toEntity(any())).thenReturn(page());
    }

    @Test
    void shouldFindByUrlHashWhenPageExists() {
        when(jpaRepository.findByUrlHash(URL_HASH)).thenReturn(Optional.of(pageDto()));

        var saved = target.findByUrlHash(URL_HASH);
        assertTrue(saved.isPresent());
    }

    @Test
    void shouldNotFindByUrlHashWhenPageDoesntExist() {
        when(jpaRepository.findByUrlHash(URL_HASH)).thenReturn(Optional.empty());

        var saved = target.findByUrlHash(URL_HASH);
        assertTrue(saved.isEmpty());
    }

    @Test
    void shouldCountPages() {
        when(jpaRepository.count()).thenReturn(PAGES_NUMBER);

        var count = target.count();
        assertEquals(PAGES_NUMBER, count);
    }
}