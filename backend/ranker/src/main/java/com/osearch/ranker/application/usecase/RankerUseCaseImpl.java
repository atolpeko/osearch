package com.osearch.ranker.application.usecase;

import static com.osearch.ranker.util.DurationLogger.withDurationLog;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.service.indexer.Indexer;
import com.osearch.ranker.domain.service.ranker.RankerService;

import java.util.HashSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

@Log4j2
@RequiredArgsConstructor
public class RankerUseCaseImpl implements RankerUseCase {
    private final Indexer indexer;
    private final RankerService rankerService;
    private final PageRepository pageRepository;
    private final IndexRepository indexRepository;

    @Override
    public void process(long pageId) {
        try {
            Runnable task = () -> {
                log.debug("Processing page with ID {}", pageId);
                processPage(pageId);
            };

            withDurationLog(task, "Page with ID " + pageId + " processed", Level.INFO);
        } catch (Exception e) {
            log.error("Page {} processing error: {}", pageId, e.getMessage());
        }
    }

    private void processPage(long id) {
        var page = pageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Page " + id + " is not indexed yet"));
        var indexes = indexer.index(page);
        for (var index: indexes) {
            var existingIndex = indexRepository.findByKeyword(index);
            var newIndex = existingIndex.orElseGet(() ->
                Index.builder()
                    .keywords(index)
                    .pages(new HashSet<>())
                    .build());
            newIndex.addPage(page);

            rankerService.rank(newIndex);

            log.debug("Saving index: {}", index);
            indexRepository.save(newIndex);
        }
    }
}
