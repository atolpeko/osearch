package com.osearch.crawler.application.service;

import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.application.port.entity.HttpResponse;
import com.osearch.crawler.domain.entity.Page;
import com.osearch.crawler.domain.service.hasher.Hasher;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;
import com.osearch.crawler.application.port.exception.HttpForbiddenException;
import com.osearch.crawler.application.port.exception.HttpToManyRequestsException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * {@link Crawler} and {@link Processor} implement producer-consumer design pattern.
 * While Crawler looks for new pages Processor processes them.
 * </p>
 *
 * The Crawler class represents a web crawler that searches for web pages.
 * It uses 'pagesToSave' blocking deque to save found pages for processing and
 * 'pagesToCrawl' blocking deque to save recursively found pages for later crawling.
 */
@Log4j2
@Builder
@AllArgsConstructor
public class Crawler implements Runnable {
    private final int id;
    private final int pagesToKeep;
    private final BlockingDeque<Page> pagesToSave;
    private final BlockingDeque<String> pagesToCrawl;

    private final HtmlProcessor htmlProcessor;
    private final HttpService httpService;
    private final Hasher hasher;

    /**
     * Executes the crawler in a separate thread. While the thread is not interrupted,
     * it continuously takes a page to crawl from the `pagesToCrawl` blocking queue and
     * processes it. If the thread is interrupted, it breaks
     * the execution loop and stops the crawler.
     */
    @Override
    public void run() {
        log.info("Running crawler with ID {}", id);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var page = pagesToCrawl.take();
                process(page);
                log.debug("Page {} crawled. {} left", page, getPagesLeft());
            } catch (InterruptedException e) {
                log.debug("Crawler {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            }
        }

        log.debug("Stopping crawler {}", id);
    }

    private void process(String hostUrl) throws InterruptedException {
        try {
            log.debug("Getting page {}", hostUrl);
            var response = httpService.get(hostUrl);
            var nestedUrls = htmlProcessor.findNestedUrls(hostUrl, response.getContent());
            var page = assemblePage(response, nestedUrls);

            pagesToSave.put(page);
            saveNestedUrls(nestedUrls);
        } catch (InterruptedException e) {
            throw e;
        } catch (HttpForbiddenException | HttpToManyRequestsException e) {
            log.error("Error getting page {}: Saving for later: {}", hostUrl, e.getMessage());
            pagesToCrawl.put(hostUrl);
        } catch (Exception e) {
            log.error("Error getting page {}. Skipping: {}", hostUrl, e.getMessage());
        }
    }

    private Page assemblePage(HttpResponse response, List<String> nestedUrls) {
        return Page.builder()
            .url(response.getUrl())
            .content(response.getContent())
            .nestedUrls(nestedUrls)
            .hash(hasher.hash(response.getContent()))
            .urlHash(hasher.hash(response.getUrl()))
            .loadTime(response.getLoadTime())
            .foundAt(LocalDateTime.now())
            .build();
    }

    private void saveNestedUrls(List<String> urls) throws InterruptedException {
        var pagesLeft = getPagesLeft();
        if (pagesLeft >= pagesToKeep) {
            log.debug("Not saving nested URLs as the number of pages"
                + " to process is to large: {}", pagesLeft);
            return;
        }

        for (var url : urls) {
            pagesToCrawl.put(url);
        }
    }

    private int getPagesLeft() {
        synchronized (pagesToCrawl) {
            return pagesToCrawl.size();
        }
    }
}
