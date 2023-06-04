package com.osearch.indexer.service;

import static com.osearch.indexer.fixture.ProcessorFixture.NESTED_URL_1;
import static com.osearch.indexer.fixture.ProcessorFixture.NESTED_URL_2;
import static com.osearch.indexer.fixture.ProcessorFixture.SAVED_ID;
import static com.osearch.indexer.fixture.ProcessorFixture.URL;
import static com.osearch.indexer.fixture.ProcessorFixture.keywordDtos;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage1;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage1Dto;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage2;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage2Dto;
import static com.osearch.indexer.fixture.ProcessorFixture.page;
import static com.osearch.indexer.fixture.ProcessorFixture.pageDto;
import static com.osearch.indexer.fixture.ProcessorFixture.request;
import static com.osearch.indexer.fixture.ProcessorFixture.savedPageDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.KeywordRepository;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.inout.repository.mapper.KeywordMapper;
import com.osearch.indexer.inout.repository.mapper.PageMapper;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class IndexerServiceImplTest {

    @InjectMocks
    IndexerServiceImpl target;

    @Mock
    ContentAnalyzer analyzer;

    @Mock
    IndexChangedMessageSender messageSender;

    @Mock
    PageRepository pageRepository;

    @Mock
    KeywordRepository keywordRepository;

    @Mock
    PageMapper pageMapper;

    @Mock
    KeywordMapper keywordMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(analyzer.analyze(request())).thenReturn(page());

        when(pageRepository.findByUrl(URL)).thenReturn(Optional.empty());
        when(pageRepository.findByUrl(NESTED_URL_1)).thenReturn(Optional.empty());
        when(pageRepository.findByUrl(NESTED_URL_2)).thenReturn(Optional.empty());
        when(pageRepository.save(any())).thenReturn(savedPageDto());

        when(keywordRepository.findByValue(any())).thenReturn(Optional.empty());
        when(keywordRepository.saveAll(any())).thenReturn(new ArrayList<>(keywordDtos()));

        when(pageMapper.toDto(page())).thenReturn(pageDto());
        when(pageMapper.toDto(nestedPage1())).thenReturn(nestedPage1Dto());
        when(pageMapper.toDto(nestedPage2())).thenReturn(nestedPage2Dto());
    }

    @Test
    void shouldProcess() {
        target.process(request());
        verify(messageSender, times(1)).send(SAVED_ID);
    }
}