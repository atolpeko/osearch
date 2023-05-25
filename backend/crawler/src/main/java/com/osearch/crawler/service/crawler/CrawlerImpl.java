package com.osearch.crawler.service.crawler;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.pageprocessor.PageProcessor;

import java.util.concurrent.BlockingDeque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Builder
public class CrawlerImpl implements Crawler {
    private final int id;
    private String initialUrl;
    private final BlockingDeque<URL> urls;

    private final PageProcessor pageProcessor;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && initialUrl != null) {
            try {
                log.info("Running crawler with ID {}. Initial URL: {}", id, initialUrl);
                findURLs(initialUrl);
                initialUrl = null;
            } catch (InterruptedException e) {
                log.info("Crawler {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            }
        }

        log.info("Crawler {} has processed all found URLs", id);
    }

    private void findURLs(String hostUrl) throws InterruptedException {
        try {
            log.info("Getting URL {}", hostUrl);
            var url = pageProcessor.process(hostUrl);
            urls.put(url);

            log.debug("Got {} new URLs from {}", url.getNestedUrls().size(), hostUrl);
            for (var nestedUrl : url.getNestedUrls()) {
                findURLs(nestedUrl);
            }
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error getting URL {}: {}. Skipping", hostUrl, e.getMessage());
        }
    }
}
