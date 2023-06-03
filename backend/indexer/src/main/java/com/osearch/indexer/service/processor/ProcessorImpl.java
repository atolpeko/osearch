package com.osearch.indexer.service.processor;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.KeywordRepository;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.inout.repository.dto.KeywordDto;
import com.osearch.indexer.inout.repository.dto.KeywordRelationDto;
import com.osearch.indexer.inout.repository.dto.PageDto;
import com.osearch.indexer.inout.repository.mapper.KeywordMapper;
import com.osearch.indexer.inout.repository.mapper.PageMapper;
import com.osearch.indexer.inout.repository.transaction.TransactionExecutor;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Builder
public class ProcessorImpl implements Processor {
    private final int id;
    private final BlockingDeque<IndexRequest> requests;

    private final IndexChangedMessageSender messageSender;
    private final ContentAnalyzer analyzer;
    private final TransactionExecutor transactionExecutor;
    private final PageRepository pageRepository;
    private final KeywordRepository keywordRepository;
    private final PageMapper pageMapper;
    private final KeywordMapper keywordMapper;

    @Override
    public void run() {
        log.info("Running processor with ID {}", id);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var start = Instant.now();
                var request = requests.take();
                log.debug("Processing request with URL {}", request.getUrl());
                processRequest(request);

                var timeElapsed = Duration.between(start, Instant.now()).toSeconds();
                log.debug("Page with URL {} processed in {} sec. {} requests left.",
                        request.getUrl(), timeElapsed,  requestsLeft());
            } catch (InterruptedException e) {
                log.debug("Processor {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            }
        }

        log.debug("Stopping processor {}", id);
    }

    private void processRequest(IndexRequest request) throws InterruptedException {
        try {
            var page = analyzer.analyze(request);
            var pageId = transactionExecutor.inTransaction(() -> {
                var dto = findPage(page);

                log.debug("Saving {} page data", request.getUrl());
                var savedPages = saveReferredPages(dto, request.getNestedUrls());
                setPageRelations(dto, savedPages);
                var savedKeywords = saveKeywords(dto, page.getKeywords());
                setKeywordRelations(dto, savedKeywords, page.getKeywords());

                return savePage(dto);
            });

            log.debug("Messaging page ID: {}", pageId);
            messageSender.send(pageId);
        } catch (Exception e) {
            log.error("Page {} indexing error: {}", request.getUrl(), e.getMessage());
        }
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

    private List<PageDto> saveReferredPages(PageDto page, List<String> nestedUrls) {
        log.debug("Checking if related for page {} pages are already saved", page.getUrl());
        var referredPages = nestedUrls.stream()
                .map(url -> Page.builder().url(url).build())
                .map(this::findPage)
                .collect(Collectors.toSet());

        log.debug("Saving {} related pages for page {}", referredPages.size(), page.getUrl());
        return pageRepository.saveAll(referredPages);
    }

    private void setPageRelations(PageDto page, List<PageDto> referred) {
        var refersTo = page.getRefersTo() == null ?
                new HashSet<PageDto>() :
                page.getRefersTo();
        refersTo.addAll(referred);
        page.setRefersTo(refersTo);
    }

    private List<KeywordDto> saveKeywords(PageDto page, Set<Keyword> keywords) {
        log.debug("Checking if keywords for page {} are already saved", page.getUrl());
        var keywordsToSave = keywords.stream()
                .map(this::findKeyword)
                .collect(Collectors.toList());

        log.debug("Saving {} keywords for page {}", keywords.size(), page.getUrl());
        return keywordRepository.saveAll(keywordsToSave);
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

    private long requestsLeft() {
        synchronized (requests) {
            return requests.size();
        }
    }
}
