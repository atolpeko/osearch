package com.osearch.crawler.application.service;

import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.domain.entity.Page;

import java.util.concurrent.BlockingDeque;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * {@link Crawler} and {@link Processor} implement producer-consumer design pattern.
 * While Crawler looks for new pages Processor processes them.
 * </p>
 *
 * The Processor is a class that processes crawled web pages.
 * It uses 'pages' blocking deque to process pages that have been crawled by
 * Crawler.
 */
@Log4j2
@Builder
@RequiredArgsConstructor
public class Processor implements Runnable {
    private final int id;
    private final BlockingDeque<Page> pages;

    private final PageMessageSender messageSender;
    private final PageRepository repository;

     /**
     * Executes the processor in a separate thread. While the thread is not interrupted,
     * it continuously takes a page from the `pages` blocking queue and
     * processes it. If the thread is interrupted, it breaks
     * the execution loop and stops the crawler.
     */
    @Override
    public void run() {
        log.info("Running processor with ID {}", id);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var page = pages.take();
                log.debug("Processing page {}", page.getUrl());
                process(page);
                log.info("Page {} crawled", page.getUrl());
            } catch (InterruptedException e) {
                log.debug("Processor {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            }
        }

        log.debug("Stopping processor {}", id);
    }

    private void process(Page page) {
        try {
            if (notProcessed(page)) {
                messagePage(page);
                savePage(page);
                log.debug("Page {} processed. {} left", page.getUrl(), getPagesLeft());
            } else {
                log.debug("Page {} is already processed. Pages left for processing: {}",
                    page.getUrl(), getPagesLeft());
            }
        } catch (Exception e) {
            log.error("Error processing page {}: {}", page.getUrl(), e.getMessage());
        }
    }

    private boolean notProcessed(Page page) {
        var saved = repository.findByUrlHash(page.getUrlHash());
        if (saved.isEmpty()) {
            return true;
        }

        var hash = saved.get().getHash();
        var changed = !hash.equals(page.getHash());
        if (changed) {
            log.debug("Page {} content has changed. Re-processing", page.getUrl());
        }

        return changed;
    }

    private synchronized void messagePage(Page page) {
        messageSender.send(page);
    }

    private synchronized void savePage(Page page) {
        log.debug("Saving {}", page.getUrl());
        repository.save(page);
    }

    private int getPagesLeft() {
        synchronized (pages) {
            return pages.size();
        }
    }
}
