package com.osearch.crawler.fixture;

import com.osearch.crawler.inout.messaging.mapper.MessageURLMapper;
import com.osearch.crawler.inout.messaging.producer.URLMessageSender;
import com.osearch.crawler.inout.repository.URLRepository;
import com.osearch.crawler.inout.repository.dto.URLDto;
import com.osearch.crawler.inout.repository.mapper.RepositoryURLMapper;
import com.osearch.crawler.service.processor.ProcessorImpl;
import com.osearch.crawler.service.entity.URL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProcessorFixture {
    public static final int PROCESSORS_COUNT = 1;

    public static final String URL_1 = "https://en.wikipedia.org/wiki/Wikipedia";
    public static final String URL_2 = "https://www.reddit.com";

    public static final String URL_1_HASH = "0a97397de682d025da31742c56eb8a85";
    public static final String URL_2_HASH = "30dd8fd5626b7e36783516a6e238baf2";

    public static final String URL_1_PAGE_HASH = "5123529358d09543036e6ad2dbf41d11";
    public static final String URL_1_CHANGED_PAGE_HASH = "3639b4f1705681256c7e79fe59d49db1";
    public static final String URL_2_PAGE_HASH = "67ab996e1dda4d631ad1ca001fdb8128";

    public static URL url1() {
        return URL.builder()
                .value(URL_1)
                .urlHash(URL_1_HASH)
                .pageHash(URL_1_PAGE_HASH)
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URL url2() {
        return URL.builder()
                .value(URL_2)
                .urlHash(URL_2_HASH)
                .pageHash(URL_2_PAGE_HASH)
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URL changedUrl1() {
        return URL.builder()
                .value(URL_1)
                .urlHash(URL_1_HASH)
                .pageHash(URL_1_CHANGED_PAGE_HASH)
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URLDto urlDto1() {
        return URLDto.builder()
                .urlHash(URL_1_HASH)
                .pageHash(URL_1_PAGE_HASH)
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URLDto urlDto2() {
        return URLDto.builder()
                .urlHash(URL_2_HASH)
                .pageHash(URL_2_PAGE_HASH)
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static List<Runnable> getProcessors(
            URLMessageSender messageSender,
            URLRepository repository,
            RepositoryURLMapper repositoryURLMapper,
            MessageURLMapper messageURLMapper,
            BlockingDeque<URL> urls
    ) {
        IntFunction<ProcessorImpl> processor = id ->
                processor(id, messageSender, repository,
                        repositoryURLMapper, messageURLMapper, urls);

        return IntStream.range(0, PROCESSORS_COUNT)
                .mapToObj(processor)
                .collect(Collectors.toList());
    }

    private static ProcessorImpl processor(
            int id,
            URLMessageSender messageSender,
            URLRepository repository,
            RepositoryURLMapper repositoryURLMapper,
            MessageURLMapper messageURLMapper,
            BlockingDeque<URL> urls

    ) {
        return ProcessorImpl.builder()
                .id(id)
                .messageProducer(messageSender)
                .repository(repository)
                .messageURLMapper(messageURLMapper)
                .repositoryURLMapper(repositoryURLMapper)
                .urls(urls)
                .build();
    }
}
