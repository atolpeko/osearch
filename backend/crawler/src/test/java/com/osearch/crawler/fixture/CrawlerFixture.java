package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.out.http.HttpResponseEntity;
import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.application.port.entity.HttpResponse;
import com.osearch.crawler.application.service.Crawler;
import com.osearch.crawler.domain.entity.Page;
import com.osearch.crawler.domain.service.hasher.Hasher;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CrawlerFixture {
    public static final int CRAWLERS_COUNT = 1;
    public static final int RESULT_URLS_COUNT = 4;

    public static final String INITIAL_URL = "https://stackoverflow.com/questions/";
    public static final String CONTENT = "";
    public static final String URL_HASH = "304c42801cd263892249f4d473f4ee72";
    public static final String PAGE_HASH = "300d41ba4d0f3468bf95e319db2c0a85";
    public static final String NESTED_URL_1 = "https://stackoverflow.com/questions/1";
    public static final String NESTED_URL_2 = "https://stackoverflow.com/questions/2";
    public static final String NESTED_URL_3 = "https://stackoverflow.com/questions/3";

    public static Page page() {
        return Page.builder()
            .url(INITIAL_URL)
            .content(CONTENT)
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .nestedUrls(List.of(NESTED_URL_1, NESTED_URL_2, NESTED_URL_3))
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static HttpResponse pageResponse() {
        return HttpResponseEntity.builder()
            .url(INITIAL_URL)
            .content(CONTENT)
            .build();
    }

    public static Page nestedPage1() {
        return Page.builder()
            .url(NESTED_URL_1)
            .content(CONTENT)
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .nestedUrls(List.of())
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static Page nestedPage2() {
        return Page.builder()
            .url(NESTED_URL_2)
            .content(CONTENT)
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .nestedUrls(List.of())
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static Page nestedPage3() {
        return Page.builder()
            .url(NESTED_URL_2)
            .content(CONTENT)
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .nestedUrls(List.of())
            .foundAt(LocalDateTime.now())
            .build();
    }

    public static List<Crawler> getCrawlers(
        BlockingDeque<String> pagesToCrawl,
        BlockingDeque<Page> pagesToSave,
        HttpService httpService,
        HtmlProcessor htmlProcessor,
        Hasher hasher
    ) {
        return IntStream.range(0, CRAWLERS_COUNT)
            .mapToObj(i -> getCrawler(i, pagesToCrawl, pagesToSave,
                httpService, htmlProcessor, hasher))
            .collect(Collectors.toList());
    }

    private static Crawler getCrawler(
        int id,
        BlockingDeque<String> pagesToCrawl,
        BlockingDeque<Page> pagesToSave,
        HttpService httpService,
        HtmlProcessor htmlProcessor,
        Hasher hasher
    ) {
        return Crawler.builder()
            .id(id)
            .pagesToKeep(100)
            .pagesToCrawl(pagesToCrawl)
            .pagesToSave(pagesToSave)
            .httpService(httpService)
            .htmlProcessor(htmlProcessor)
            .hasher(hasher)
            .build();
    }
}
