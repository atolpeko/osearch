package com.osearch.crawler.service.processor;

import com.osearch.crawler.inout.messaging.mapper.MessageURLMapper;
import com.osearch.crawler.inout.messaging.producer.URLMessageSender;
import com.osearch.crawler.inout.repository.URLRepository;
import com.osearch.crawler.inout.repository.dto.URLDto;
import com.osearch.crawler.inout.repository.mapper.RepositoryURLMapper;
import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.util.exception.ValueException;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Builder
public class ProcessorImpl implements Processor {
    private final int id;

    private final BlockingDeque<URL> urls;

    private final URLMessageSender messageProducer;
    private final URLRepository repository;
    private final RepositoryURLMapper repositoryURLMapper;
    private final MessageURLMapper messageURLMapper;

    @Override
    public void run() {
        log.info("Running processor with ID {}", id);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                var url = urls.take();
                log.debug("Processing URL {}", url.getValue());
                processUrl(url);
            } catch (InterruptedException e) {
                log.debug("Processor {} interrupted", id);
                Thread.currentThread().interrupt();
                break;
            } catch (ValueException e) {
                log.error("Error processing URL {}: {}", e.getValue(), e.getMessage());
            }
        }

        log.debug("Stopping processor {}", id);
    }

    private void processUrl(URL url) {
        try {
            if (!urlIsProcessed(url)) {
                messageUrl(url);
                saveUrl(url);
                log.info("URL {} processed. {} left", url.getValue(), getUrlsLeft());
            } else {
                log.debug("URL {} is already processed. URLs left for processing: {}",
                        url.getValue(), getUrlsLeft());
            }
        } catch (Exception e) {
            throw new ValueException(url, e.getMessage(), e);
        }
    }

    private boolean urlIsProcessed(URL url) {
        var dto = repository.findByUrlHash(url.getUrlHash());
        if (dto.isPresent()) {
            var hash = dto.get().getPageHash();
            boolean changed = !hash.equals(url.getPageHash());
            if (changed) {
                log.debug("URL {} page content has changed. Re-processing", url.getValue());
            }

            return !changed;
        }

        return false;
    }

    private synchronized void messageUrl(URL url) {
        var dto = messageURLMapper.toDto(url);
        messageProducer.send(dto);
    }

    private synchronized void saveUrl(URL url) {
        URLDto dto;
        var savedOptional = repository.findByUrlHash(url.getUrlHash());
        if (savedOptional.isPresent()) {
            dto = savedOptional.get();
            dto.setPageHash(url.getPageHash());
            dto.setFoundAt(LocalDateTime.now());
        } else {
            dto = repositoryURLMapper.toDto(url);
        }

        log.debug("Saving {}", url.getValue());
        repository.save(dto);
    }

    private int getUrlsLeft() {
        synchronized (urls) {
            return urls.size();
        }
    }
}
