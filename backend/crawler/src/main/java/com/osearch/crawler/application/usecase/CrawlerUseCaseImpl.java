package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.properties.ApplicationProperties;
import com.osearch.crawler.application.service.Crawler;
import com.osearch.crawler.application.usecase.exception.CrawlerServiceException;
import com.osearch.crawler.domain.entity.Page;
import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.domain.service.executor.BackgroundExecutor;
import com.osearch.crawler.domain.service.hasher.Hasher;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;
import com.osearch.crawler.application.service.Processor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class CrawlerUseCaseImpl implements CrawlerUseCase {
    private final BackgroundExecutor executor;
    private final HttpService httpService;
    private final HtmlProcessor htmlProcessor;
    private final Hasher hasher;
    private final PageMessageSender messageSender;
    private final PageRepository repository;
    private final ApplicationProperties properties;

    private final BlockingDeque<String> pagesToCrawl = new LinkedBlockingDeque<>();
    private final BlockingDeque<Page> pagesToSave = new LinkedBlockingDeque<>();

    @Override
    public synchronized void start(List<String> initialUrls) {
        try {
            if (isRunning()) {
                throw new CrawlerAlreadyRunningException("Crawler is already running");
            }

            log.info("Starting crawler with initial URLs {}",
                Arrays.toString(initialUrls.toArray()));
            pagesToCrawl.addAll(initialUrls);
            var tasks = getTasks();
            executor.execute(tasks);
        } catch (CrawlerServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new CrawlerServiceException(e.getMessage(), e);
        }
    }

    @Override
    public synchronized boolean isRunning() {
        try {
            return executor.isRunning();
        } catch (Exception e) {
            throw new CrawlerServiceException(e.getMessage(), e);
        }
    }

    private List<Runnable> getTasks() {
        return Stream.concat(getCrawlers(), getProcessors())
            .collect(Collectors.toList());
    }

    private Stream<Runnable> getCrawlers() {
        IntFunction<Runnable> crawler = id ->
            Crawler.builder()
                .id(id)
                .pagesToKeep(properties.getPagesToKeepCount())
                .pagesToCrawl(pagesToCrawl)
                .pagesToSave(pagesToSave)
                .httpService(httpService)
                .htmlProcessor(htmlProcessor)
                .hasher(hasher)
                .build();

        return IntStream.range(0, properties.getCrawlerThreadsCount())
            .mapToObj(crawler);
    }

    private Stream<Runnable> getProcessors() {
        IntFunction<Runnable> processor = id ->
            Processor.builder()
                .id(id)
                .pages(pagesToSave)
                .messageSender(messageSender)
                .repository(repository)
                .build();

        return IntStream.range(0, properties.getProcessorThreadsCount())
            .mapToObj(processor);
    }

    @Override
    public synchronized void stop() {
        try {
            if (!isRunning()) {
                throw new CrawlerNotRunningException("Crawler not running");
            }

            log.info("Stopping crawler service");
            executor.stop();
            pagesToSave.clear();
            pagesToCrawl.clear();
        } catch (CrawlerServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new CrawlerServiceException(e.getMessage(), e);
        }
    }
}
