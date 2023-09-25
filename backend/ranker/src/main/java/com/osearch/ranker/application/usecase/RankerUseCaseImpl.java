package com.osearch.ranker.application.usecase;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.domain.service.Ranker;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class RankerUseCaseImpl implements RankerUseCase {
    private final Ranker ranker;
    private final PageRepository pageRepository;
    private final IndexRepository indexRepository;

    @Override
    public void process(long pageId) {
        try {
            var start = Instant.now();
            log.info("Processing page with ID {}", pageId);
            processPage(pageId);
            var timeElapsed = Duration.between(start, Instant.now());
            log.info("Page with ID {} processed in {} ",
                pageId, formatDuration(timeElapsed));
        } catch (DataAccessException e) {
            log.error("Page {} processing error. DB not available. {}", pageId, e.getMessage());
        } catch (Exception e) {
            log.error("Page {} processing error: {}", pageId, e.getMessage());
        }
    }

    private void processPage(long id) {
        var page = pageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Page " + id + " is not indexed yet"));
        var counter = 0;
        for (var topic: page.getTopics()) {
            var index = getIndex(topic.getName());
            index.addPage(page);
            loadReferredPages(index);
            ranker.rank(index);

            log.debug("Saving index: {}", index.getTopic());
            indexRepository.save(index);
            log.debug("Index {} processed. {} left",
                index.getTopic(), page.getTopics().size() - ++counter);
        }

        log.info("{} indexes processed for page {}",
            page.getTopics().size(), page.getUrl());
    }

    private Index getIndex(String topic) {
        Index index;
        var existingIndex = indexRepository.findByTopic(topic);
        if (existingIndex.isPresent()) {
            index = existingIndex.get();
            loadPages(index);
        } else {
            index = Index.builder()
                .topic(topic)
                .pages(new HashSet<>())
                .build();
        }

        return index;
    }

    private void loadPages(Index index) {
        var pages = index.getPages();
        var toRemove = new HashSet<Page>();
        for (var page: pages) {
            var data = pageRepository.findById(page.getSourceId());
            if (data.isEmpty()) {
                toRemove.add(page);
                continue;
            }

            page.setTitle(data.get().getTitle());
            page.setTopics(data.get().getTopics());
            page.setReferredPages(data.get().getReferredPages());
            page.setLoadTime(data.get().getLoadTime());
        }

        pages.removeAll(toRemove);
    }

    private void loadReferredPages(Index index) {
        for (var page: index.getPages()) {
            for (var referred: page.getReferredPages()) {
                var saved = indexRepository.getPage(index.getTopic(), referred.getUrl());
                saved.ifPresent(value -> referred.setTotalRank(value.getTotalRank()));
            }
        }
    }

    private String formatDuration(Duration duration) {
        if (duration.toMillis() >= 60000) {
            return String.format("%d minutes", duration.toMinutes());
        }

        return duration.toMillis() >= 1000
            ? String.format("%d seconds", duration.toSeconds())
            : String.format("%d ms", duration.toMillis());
    }
}
