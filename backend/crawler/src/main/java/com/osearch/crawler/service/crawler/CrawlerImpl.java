package com.osearch.crawler.service.crawler;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.pageprocessor.PageProcessor;
import com.osearch.crawler.service.rest.exception.RestForbiddenException;
import com.osearch.crawler.service.rest.exception.RestToManyRequestsException;

import java.util.List;
import java.util.concurrent.BlockingDeque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Builder
public class CrawlerImpl implements Crawler {
    private final int id;
    private final int urlsToKeep;

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
            }
        }

        log.debug("Stopping crawler {}", id);
    }

    private void processUrl(String hostUrl) throws InterruptedException {
        try {
            log.debug("Getting URL {}", hostUrl);
            var url = pageProcessor.process(hostUrl);
            urlsToSave.put(url);
            processNestedUrls(url.getNestedUrls());
        } catch (RestForbiddenException | RestToManyRequestsException e) {
            log.error("Error getting URL {}: Saving for later: {}", hostUrl, e.getMessage());
            urlsToGet.put(hostUrl);
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error getting URL {}. Skipping: {}", hostUrl, e.getMessage());
        }
    }

    private void processNestedUrls(List<String> urls) throws InterruptedException {
        var urlsNumber = getUrlsLeft();
        if (urlsNumber >= urlsToKeep) {
            log.debug("Not saving nested URLs as the number of URLs"
                    + " to process is to large: {}", urlsNumber);
            return;
        }

        for (var url : urls) {
            urlsToGet.put(url);
        }
    }

    private int getUrlsLeft() {
        synchronized (urlsToGet) {
            return urlsToGet.size();
        }
    }
}
