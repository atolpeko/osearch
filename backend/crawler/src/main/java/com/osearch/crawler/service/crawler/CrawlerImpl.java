package com.osearch.crawler.service.crawler;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.pageprocessor.PageProcessor;
import com.osearch.crawler.util.exception.ValueException;

import java.util.concurrent.BlockingDeque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Builder
public class CrawlerImpl implements Crawler {
    private final int id;
    private final BlockingDeque<URL> urlsToSave;
    private final BlockingDeque<String> urlsToGet;

    private final PageProcessor pageProcessor;

    @Override
    public void run() {
        log.info("Running crawler with ID {}", id);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var url = urlsToGet.take();
                processUrl(url);
                log.info("URL {} crawled. {} left", url, getUrlsLeft());
            } catch (InterruptedException e) {
                log.debug("Crawler {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            } catch (ValueException e) {
                log.error("Error getting URL {}: {}. Skipping", e, e.getMessage(), e);
            }
        }

        log.info("Stopping crawler {}", id);
    }

    private void processUrl(String hostUrl) throws InterruptedException {
        try {
            log.debug("Getting URL {}", hostUrl);
            var url = pageProcessor.process(hostUrl);

            urlsToSave.put(url);
            for (var nestedUrl : url.getNestedUrls()) {
                urlsToGet.put(nestedUrl);
            }
        } catch (Exception e) {
            throw new ValueException(hostUrl, e.getMessage(), e);
        }
    }

    private int getUrlsLeft() {
        synchronized (urlsToGet) {
            return urlsToGet.size();
        }
    }
}
