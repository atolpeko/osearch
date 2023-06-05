package com.osearch.indexer.service;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;
import com.osearch.indexer.service.entity.IndexRequest;

import java.time.Duration;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class IndexerServiceImpl implements IndexerService {
    private final IndexChangedMessageSender messageSender;
    private final ContentAnalyzer analyzer;
    private final PageRepository repository;

    @Override
    public void process(IndexRequest request) {
        try {
            var start = Instant.now();
            log.debug("Processing request with URL {}", request.getUrl());
            processRequest(request);
            var timeElapsed = Duration.between(start, Instant.now());
            log.info("Page with URL {} processed in {} ",
                    request.getUrl(), formatDuration(timeElapsed));
        } catch (Exception e) {
            log.error("Page {} indexing error: {}", request.getUrl(), e.getMessage());
        }
    }

    private void processRequest(IndexRequest request) {
        var page = analyzer.analyze(request);
        page.setNestedUrls(request.getNestedUrls());
        long pageId = repository.save(page);

        log.debug("Messaging page ID: {}", pageId);
        messageSender.send(pageId);
    }

    private String formatDuration(Duration duration) {
        return duration.toMillis() >= 1000
                ? String.format("%d seconds", duration.toSeconds())
                : String.format("%d ms", duration.toMillis());
    }
}
