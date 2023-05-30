package com.osearch.indexer.service;

import com.osearch.indexer.config.properties.ServiceProperties;
import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.KeywordRepository;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.inout.repository.mapper.PageMapper;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.executor.BackgroundExecutor;
import com.osearch.indexer.service.processor.Processor;
import com.osearch.indexer.service.processor.ProcessorImpl;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class IndexerServiceImpl implements IndexerService {
    private final BackgroundExecutor executor;
    private final IndexChangedMessageSender messageSender;
    private final PageRepository pageRepository;
    private final KeywordRepository keywordRepository;
    private final PageMapper mapper;
    private final ContentAnalyzer contentAnalyzer;
    private final ServiceProperties properties;

    private final BlockingDeque<IndexRequest> requestsToProcess = new LinkedBlockingDeque<>();

    @Override
    public void process(IndexRequest request) {
        try {
            if (!executor.isRunning()) {
                log.debug("Starting indexing service");
                executor.execute(getProcessors());
            }

            requestsToProcess.put(request);
        } catch (InterruptedException e) {
            log.debug("Index service interrupted");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Index error: {}", e.getMessage());
        }
    }

    private List<Processor> getProcessors() {
        IntFunction<Processor> processor = id ->
                ProcessorImpl.builder()
                        .id(id)
                        .requests(requestsToProcess)
                        .messageSender(messageSender)
                        .pageRepository(pageRepository)
                        .keywordRepository(keywordRepository)
                        .mapper(mapper)
                        .analyzer(contentAnalyzer)
                        .build();

        return IntStream.range(0, properties.getThreadsCount())
                .mapToObj(processor)
                .collect(Collectors.toList());
    }
}
