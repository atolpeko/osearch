package com.osearch.crawler.fixture;

import com.osearch.crawler.service.crawler.Crawler;
import com.osearch.crawler.service.crawler.CrawlerImpl;
import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.pageprocessor.PageProcessor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrawlerFixture {
    public static final int CRAWLERS_COUNT = 1;
    public static final int RESULT_URLS_COUNT = 4;

    public static final String INITIAL_URL = "https://stackoverflow.com/questions/";
    public static final String URL_HASH = "304c42801cd263892249f4d473f4ee72";
    public static final String PAGE_HASH = "300d41ba4d0f3468bf95e319db2c0a85";
    public static final String NESTED_URL_1 = "https://stackoverflow.com/questions/1";
    public static final String NESTED_URL_2 = "https://stackoverflow.com/questions/2";
    public static final String NESTED_URL_3 = "https://stackoverflow.com/questions/3";

    public static URL initialUrl() {
        return URL.builder()
                .value(INITIAL_URL)
                .urlHash(URL_HASH)
                .pageHash(PAGE_HASH)
                .nestedUrls(List.of(NESTED_URL_1, NESTED_URL_2, NESTED_URL_3))
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URL nestedUrl1() {
        return URL.builder()
                .value(NESTED_URL_1)
                .urlHash(URL_HASH)
                .pageHash(PAGE_HASH)
                .nestedUrls(List.of())
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URL nestedUrl2() {
        return URL.builder()
                .value(NESTED_URL_2)
                .urlHash(URL_HASH)
                .pageHash(PAGE_HASH)
                .nestedUrls(List.of())
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static URL nestedUrl3() {
        return URL.builder()
                .value(NESTED_URL_2)
                .urlHash(URL_HASH)
                .pageHash(PAGE_HASH)
                .nestedUrls(List.of())
                .foundAt(LocalDateTime.now())
                .build();
    }

    public static List<Crawler> getCrawlers(
            BlockingDeque<String> urlsToGet,
            BlockingDeque<URL> urlsToSave,
            PageProcessor pageProcessor
    ) {
        return IntStream.range(0, CRAWLERS_COUNT)
                .mapToObj(i -> getCrawler(i, urlsToGet, urlsToSave, pageProcessor))
                .collect(Collectors.toList());
    }

    private static CrawlerImpl getCrawler(
            int id,
            BlockingDeque<String> urlsToGet,
            BlockingDeque<URL> urlsToSave,
            PageProcessor pageProcessor
    ) {
        return CrawlerImpl.builder()
                .id(id)
                .urlsToKeep(100)
                .urlsToGet(urlsToGet)
                .urlsToSave(urlsToSave)
                .pageProcessor(pageProcessor)
                .build();
    }
}
