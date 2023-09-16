package com.osearch.indexer.application.usecase;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.domain.analyzer.ContentAnalyzer;
import com.osearch.indexer.domain.entity.IndexRequest;

import java.time.Duration;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class IndexerUseCaseImpl implements IndexerUseCase {
    private final ContentAnalyzer analyzer;
    private final PageRepository repository;
    private final PageMessageSender messageSender;

    @Override
    public void process(IndexRequest request) {
        try {
            var start = Instant.now();
            log.debug("Processing page with URL {}", request.getUrl());
            processRequest(request);
            var timeElapsed = Duration.between(start, Instant.now());
            log.info("Page with URL {} indexed in {} ",
                request.getUrl(), formatDuration(timeElapsed));
        } catch (Exception e) {
            log.error("Page {} indexing error: {}", request.getUrl(), e.getMessage());
        }
    }

    private void processRequest(IndexRequest request) {
        var page = analyzer.analyze(request);
        page.setNestedUrls(request.getNestedUrls());
        repository.save(page);
        messageSender.send(page);
    }

    private String formatDuration(Duration duration) {
        return duration.toMillis() >= 1000
            ? String.format("%d seconds", duration.toSeconds())
            : String.format("%d ms", duration.toMillis());
    }
}
