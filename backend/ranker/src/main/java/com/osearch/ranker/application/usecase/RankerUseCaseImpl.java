package com.osearch.ranker.application.usecase;

import static com.osearch.ranker.util.DurationLogger.withDurationLog;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
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
        } catch (DataAccessException e) {
            log.error("Page {} processing error. DB not available. {}", pageId, e.getMessage());
        } catch (Exception e) {
            log.error("Page {} processing error: {}", pageId, e.getMessage());
        }
    }

    private void processPage(long id) {
        var page = pageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Page " + id + " is not indexed yet"));
        var indexes = indexer.index(page);
        for (var index: indexes) {
            Index newIndex;
            var existingIndex = indexRepository.findByKeyword(index);
            if (existingIndex.isPresent()) {
                newIndex = existingIndex.get();
                loadPages(newIndex);
            } else {
                newIndex = Index.builder()
                    .keywords(index)
                    .pages(new HashSet<>())
                    .build();
            }

            newIndex.addPage(page);
            loadReferredPages(newIndex);
            rankerService.rank(newIndex);

            log.debug("Saving index: {}", index);
            indexRepository.save(newIndex);
        }
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
            page.setKeywords(data.get().getKeywords());
            page.setReferredPages(data.get().getReferredPages());
            page.setLoadTime(data.get().getLoadTime());
        }

        pages.removeAll(toRemove);
    }

    private void loadReferredPages(Index index) {
        for (var page: index.getPages()) {
            for (var referred: page.getReferredPages()) {
                var saved = indexRepository.getPage(index.getKeywords(), referred.getUrl());
                saved.ifPresent(value -> referred.setTotalRank(value.getTotalRank()));
            }
        }
    }
}
