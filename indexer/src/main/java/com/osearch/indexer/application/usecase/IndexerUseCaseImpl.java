package com.osearch.indexer.application.usecase;

import static com.osearch.indexer.util.DurationFormatter.formatDuration;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.application.port.exception.DataAccessException;
import com.osearch.indexer.application.usecase.exception.IndexerException;
import com.osearch.indexer.domain.IndexService;
import com.osearch.indexer.domain.valueobject.IndexRequest;

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
        } catch (DataAccessException e) {
            var msg = "Page " + request.getUrl() + " indexing error. "
                + "DB not available: " + e.getMessage();
            throw new IndexerException(msg);
        } catch (Exception e) {
            var msg = "Page " + request.getUrl() + " indexing error: " + e.getMessage();
            throw new IndexerException(msg);
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
        try {
            return repository.countIndexed();
        } catch (DataAccessException e) {
            throw new IndexerException("DB not available: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IndexerException(e.getMessage(), e);
        }
    }
}
