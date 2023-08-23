package com.osearch.crawler.fixture;

import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.service.Processor;
import com.osearch.crawler.domain.entity.Page;

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

    public static Page page1() {
        return Page.builder()
            .url(URL_1)
            .urlHash(URL_1_HASH)
            .hash(URL_1_PAGE_HASH)
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static Page page2() {
        return Page.builder()
            .url(URL_2)
            .urlHash(URL_2_HASH)
            .hash(URL_2_PAGE_HASH)
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static Page changedPage1() {
        return Page.builder()
            .url(URL_1)
            .urlHash(URL_1_HASH)
            .hash(URL_1_CHANGED_PAGE_HASH)
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static List<Processor> getProcessors(
        PageMessageSender messageSender,
        PageRepository repository,
        BlockingDeque<Page> pages
    ) {
        IntFunction<Processor> processor = id ->
            processor(id, messageSender, repository, pages);

        return IntStream.range(0, PROCESSORS_COUNT)
            .mapToObj(processor)
            .collect(Collectors.toList());
    }

    private static Processor processor(
        int id,
        PageMessageSender messageSender,
        PageRepository repository,
        BlockingDeque<Page> pages
    ) {
        return Processor.builder()
            .id(id)
            .messageSender(messageSender)
            .repository(repository)
            .pages(pages)
            .build();
    }
}
