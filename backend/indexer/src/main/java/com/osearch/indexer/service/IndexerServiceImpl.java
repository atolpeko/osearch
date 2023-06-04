package com.osearch.indexer.service;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.KeywordRepository;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.inout.repository.dto.KeywordDto;
import com.osearch.indexer.inout.repository.dto.KeywordRelationDto;
import com.osearch.indexer.inout.repository.dto.PageDto;
import com.osearch.indexer.inout.repository.mapper.KeywordMapper;
import com.osearch.indexer.inout.repository.mapper.PageMapper;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class IndexerServiceImpl implements IndexerService {
    private final IndexChangedMessageSender messageSender;
    private final ContentAnalyzer analyzer;
    private final PageRepository pageRepository;
    private final KeywordRepository keywordRepository;
    private final PageMapper pageMapper;
    private final KeywordMapper keywordMapper;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    @Override
    @Transactional
    public void process(IndexRequest request) {
        try {
            var start = Instant.now();
            log.debug("Processing request with URL {}", request.getUrl());
            processRequest(request);

            var timeElapsed = Duration.between(start, Instant.now()).toSeconds();
            log.debug("Page with URL {} processed in {} sec.", request.getUrl(), timeElapsed);
        } catch (InterruptedException e) {
            log.error("Index service interrupted");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Page {} indexing error: {}", request.getUrl(), e.getMessage());
        }
    }

    private void processRequest(IndexRequest request) throws InterruptedException, ExecutionException {
        var page = analyzer.analyze(request);
        var dto = findPage(page);
        log.debug("Saving {} page data", request.getUrl());

        var pagesFuture = CompletableFuture.supplyAsync(() ->
                        saveReferredPages(request.getNestedUrls()), executor)
                .thenAcceptAsync(nested -> setPageRelations(dto, nested), executor);
        var keywordsFuture = CompletableFuture.supplyAsync(() ->
                        saveKeywords(page.getKeywords()), executor)
                .thenAcceptAsync(keywords ->
                        setKeywordRelations(dto, keywords, page.getKeywords()), executor);
        var pageId = CompletableFuture.allOf(pagesFuture, keywordsFuture)
                .thenApplyAsync(voidResult -> savePage(dto), executor)
                .get();

        log.debug("Messaging page ID: {}", pageId);
        messageSender.send(pageId);
    }

    private PageDto findPage(Page page) {
        var dto = pageMapper.toDto(page);
        var url = page.getUrl();

        var existingDto = pageRepository.findByUrl(url);
        if (existingDto.isPresent()) {
            var saved = existingDto.get();
            saved.merge(dto);
            return saved;
        } else {
            dto.setIsIndexed(false);
            return dto;
        }
    }

    private List<PageDto> saveReferredPages(List<String> nestedUrls) {
        log.debug("Checking if related pages are already saved");
        var referredPages = nestedUrls.stream()
                .map(url -> Page.builder().url(url).build())
                .map(this::findPage)
                .collect(Collectors.toSet());

        log.debug("Saving {} related pages", referredPages.size());
        return pageRepository.saveAll(referredPages);
    }

    private List<KeywordDto> saveKeywords(Set<Keyword> keywords) {
        log.debug("Checking if keywords are already saved");
        var keywordsToSave = keywords.stream()
                .map(this::findKeyword)
                .collect(Collectors.toList());

        log.debug("Saving {} keywords", keywords.size());
        return keywordRepository.saveAll(keywordsToSave);
    }

    private void setPageRelations(PageDto page, List<PageDto> referred) {
        var refersTo = page.getRefersTo() == null ?
                new HashSet<PageDto>() :
                page.getRefersTo();
        refersTo.addAll(referred);
        page.setRefersTo(refersTo);
    }

    private void setKeywordRelations(
            PageDto page,
            List<KeywordDto> savedKeywords,
            Set<Keyword> keywords
    ) {
        var keywordRelations = new HashSet<KeywordRelationDto>();
        for (var saved : savedKeywords) {
            var occurrences = keywords.stream()
                    .filter(keyword -> keyword.getValue().equals(saved.getValue()))
                    .map(Keyword::getOccurrences)
                    .findFirst()
                    .orElse(1L);
            var relation = KeywordRelationDto.builder()
                    .keyword(saved)
                    .occurrences(occurrences)
                    .build();

            keywordRelations.add(relation);
        }

        page.setKeywordRelations(keywordRelations);
    }

    private KeywordDto findKeyword(Keyword keyword) {
        var value = keyword.getValue();
        var existingDto = keywordRepository.findByValue(value);
        return existingDto.orElseGet(() -> keywordMapper.toDto(keyword));
    }

    private long savePage(PageDto page) {
        log.debug("Saving page {}", page.getUrl());
        page.setIsIndexed(true);
        var saved = pageRepository.save(page);
        return saved.getId();
    }
}
