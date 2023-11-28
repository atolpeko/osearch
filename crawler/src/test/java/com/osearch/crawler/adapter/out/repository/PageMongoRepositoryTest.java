package com.osearch.crawler.adapter.out.repository;

import static com.osearch.crawler.fixture.PageMongoRepositoryFixture.PAGE;
import static com.osearch.crawler.fixture.PageMongoRepositoryFixture.PAGES_NUMBER;
import static com.osearch.crawler.fixture.PageMongoRepositoryFixture.PAGE_DTO;
import static com.osearch.crawler.fixture.PageMongoRepositoryFixture.UPDATED_PAGE;
import static com.osearch.crawler.fixture.PageMongoRepositoryFixture.UPDATED_PAGE_DTO;
import static com.osearch.crawler.fixture.PageMongoRepositoryFixture.URL_HASH;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.crawler.adapter.out.repository.jpa.PageDtoJpaRepository;
import com.osearch.crawler.adapter.out.repository.mapper.PageJpaMapper;
import com.osearch.crawler.application.port.exception.DataAccessException;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class PageMongoRepositoryTest {

    @InjectMocks
    PageMongoRepository target;

    @Mock
    PageDtoJpaRepository jpaRepository;

    @Mock
    PageJpaMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mapper.toDto(PAGE)).thenReturn(PAGE_DTO);
        when(mapper.toEntity(any())).thenReturn(PAGE);
        when(mapper.toDto(UPDATED_PAGE)).thenReturn(UPDATED_PAGE_DTO);
    }

    @Test
    void shouldFindByUrlHashWhenPageExists() {
        when(jpaRepository.findByUrlHash(URL_HASH)).thenReturn(Optional.of(PAGE_DTO));

        var saved = target.findByUrlHash(URL_HASH);
        assertEquals(PAGE, saved.get());
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

    @Test
    void shouldSaveNewPages() {
        when(jpaRepository.findByUrlHash(PAGE.getUrlHash()))
            .thenReturn(Optional.empty());

        target.save(PAGE);
        verify(jpaRepository, times(1)).save(PAGE_DTO);
    }

    @Test
    void shouldUpdateSavedPages() {
        when(jpaRepository.findByUrlHash(PAGE.getUrlHash()))
            .thenReturn(Optional.of(PAGE_DTO));

        target.save(UPDATED_PAGE);
        verify(jpaRepository, times(1)).save(UPDATED_PAGE_DTO);
    }

    @Test
    void shouldThrowDataAccessExceptionIfErrorHappens() {
        when(jpaRepository.findByUrlHash(PAGE.getUrlHash())).thenThrow();

        assertThrows(DataAccessException.class,
            () -> target.findByUrlHash(PAGE.getUrlHash()));
    }
}