package com.osearch.indexer.application.usecase;

import static com.osearch.indexer.util.DurationFormatter.formatDuration;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.domain.IndexService;
import com.osearch.indexer.domain.entity.IndexRequest;

import java.time.Duration;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class IndexerUseCaseImpl implements IndexerUseCase {
    private final IndexService indexService;
    private final PageRepository repository;
    private final PageMessageSender messageSender;

    @Override
    public void process(IndexRequest request) {
        try {
            var start = Instant.now();
            log.info("Indexing page with URL {}", request.getUrl());
            processRequest(request);
            var timeElapsed = Duration.between(start, Instant.now());
            log.info("Page with URL {} indexed in {} ",
                request.getUrl(), formatDuration(timeElapsed));
        } catch (Exception e) {
            log.error("Page {} indexing error: {}", request.getUrl(), e.getMessage());
        }
    }

    private void processRequest(IndexRequest request) {
        var page = indexService.index(request);
        page.setNestedUrls(request.getNestedUrls());
        var id = repository.save(page);
        page.setId(id);
        messageSender.send(page);
    }

    @Override
    public int countIndexed() {
        return repository.countIndexed();
    }
}
