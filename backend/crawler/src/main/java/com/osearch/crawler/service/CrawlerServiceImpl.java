package com.osearch.crawler.service;

import com.osearch.crawler.config.properties.ServiceProperties;
import com.osearch.crawler.inout.messaging.producer.URLMessageSender;
import com.osearch.crawler.inout.messaging.mapper.MessageURLMapper;
import com.osearch.crawler.inout.repository.URLRepository;
import com.osearch.crawler.inout.repository.mapper.RepositoryURLMapper;
import com.osearch.crawler.service.crawler.Crawler;
import com.osearch.crawler.service.crawler.CrawlerImpl;
import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.service.exception.CrawlerNotRunningException;
import com.osearch.crawler.service.executor.BackgroundExecutor;
import com.osearch.crawler.service.pageprocessor.PageProcessor;
import com.osearch.crawler.service.processor.Processor;
import com.osearch.crawler.service.processor.ProcessorImpl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CrawlerServiceImpl implements CrawlerService {
    private final BackgroundExecutor executor;
    private final PageProcessor pageProcessor;
    private final URLMessageSender messageProducer;
    private final URLRepository repository;
    private final RepositoryURLMapper repositoryURLMapper;
    private final MessageURLMapper messageURLMapper;

    private final BlockingDeque<URL> urls = new LinkedBlockingDeque<>();

    @Override
    public synchronized void start(List<String> initialUrls) {
        if (isRunning()) {
            throw new CrawlerAlreadyRunningException("Crawler is already running");
        }

        log.info("Starting crawler service with initial URLs {}",
                Arrays.toString(initialUrls.toArray()));
        var tasks = getTasks(initialUrls);
        executor.execute(tasks);
    }

    @Override
    public synchronized boolean isRunning() {
        return executor.isRunning();
    }

    private List<Runnable> getTasks(List<String> initialUrls) {
        return Stream.concat(
                getCrawlers(urls, initialUrls, ServiceProperties.getCrawlerThreadsCount()),
                getProcessors(urls, ServiceProperties.getProcessorThreadsCount())
        ).collect(Collectors.toList());
    }

    private Stream<Runnable> getCrawlers(
            BlockingDeque<URL> urls,
            List<String> initialUrls,
            int count
    ) {
        BiFunction<Integer, String, Crawler> crawler = (id, url) ->
                CrawlerImpl.builder()
                        .id(id)
                        .initialUrl(url)
                        .urls(urls)
                        .pageProcessor(pageProcessor)
                        .build();
        IntFunction<Crawler> mapper = i -> {
            if (initialUrls.size() >= count) {
                return crawler.apply(i, initialUrls.get(i));
            } else {
                var size = initialUrls.size();
                return (i < size) ? crawler.apply(i, initialUrls.get(i))
                        : crawler.apply(i, initialUrls.get(size - 1));
            }
        };

        return IntStream.range(0, count).mapToObj(mapper);
    }

    private Stream<Runnable> getProcessors(BlockingDeque<URL> urls, int count) {
        IntFunction<Processor> processor = id ->
                ProcessorImpl.builder()
                        .id(id)
                        .urls(urls)
                        .messageProducer(messageProducer)
                        .repository(repository)
                        .messageURLMapper(messageURLMapper)
                        .repositoryURLMapper(repositoryURLMapper)
                        .build();

        return IntStream.range(0, count).mapToObj(processor);
    }

    @Override
    public synchronized void stop() {
        if (!isRunning()) {
            throw new CrawlerNotRunningException("Crawler not running");
        }

        log.info("Stopping crawler service");
        executor.stop();
        urls.clear();
    }

    @Override
    public long urlsProcessed() {
        return repository.count();
    }
}
