package com.osearch.indexer.service.processor;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.KeywordRepository;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.inout.repository.dto.KeywordDto;
import com.osearch.indexer.inout.repository.dto.PageDto;
import com.osearch.indexer.inout.repository.mapper.PageMapper;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Page;

import java.util.HashSet;
import java.util.List;
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
    private final PageRepository pageRepository;
    private final KeywordRepository keywordRepository;
    private final PageMapper mapper;
    private final ContentAnalyzer analyzer;

    @Override
    public void run() {
        log.info("Running processor with ID {}", id);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var request = requests.take();
                log.debug("Processing request with URL {}", request.getUrl());
                processRequest(request);
                log.debug("Page with URL {} processed", request.getUrl());
            } catch (InterruptedException e) {
                log.debug("Processor {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            }
        }

        log.debug("Stopping processor {}", id);
    }

    private void processRequest(IndexRequest request) {
        try {
            var page = analyzer.analyze(request);
            var dto = getDto(page);
            setRelations(dto, request.getNestedUrls());
            loadKeywords(dto);
            dto.setIsIndexed(true);

            log.debug("Saving {} data", request.getUrl());
            var saved = pageRepository.save(dto);

            log.debug("Messaging page ID: {}", saved.getId());
            messageSender.send(saved.getId());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private PageDto getDto(Page page) {
        var dto = mapper.toDto(page);
        var existingPageDto = pageRepository.findByUrl(page.getUrl());
        if (existingPageDto.isPresent()) {
            var saved = existingPageDto.get();
            dto = saved.merge(dto);
        }

        return dto;
    }

    private void setRelations(PageDto page, List<String> nestedUrls) {
        var referredPages = nestedUrls.stream()
                .map(url -> Page.builder().url(url).build())
                .map(this::getDto)
                .collect(Collectors.toSet());

        for (var referredPage: referredPages) {
            var refersTo = page.getRefersTo() == null ?
                    new HashSet<PageDto>() :
                    page.getRefersTo();
            refersTo.add(referredPage);
            page.setRefersTo(refersTo);

            var referredBy = referredPage.getReferredBy() == null ?
                    new HashSet<PageDto>() :
                    referredPage.getReferredBy();
            referredBy.add(page);
            referredPage.setReferredBy(referredBy);
        }
    }

    private void loadKeywords(PageDto page) {
        var keywords = new HashSet<KeywordDto>();
        for (var keyword: page.getKeywords()) {
            var saved = keywordRepository.findByValue(keyword.getValue());
            if (saved.isPresent()) {
                keywords.add(saved.get());
            } else {
                keywords.add(keyword);
            }
        }

        page.setKeywords(keywords);
    }
}
