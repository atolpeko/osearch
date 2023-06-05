package com.osearch.indexer.fixture;

import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class IndexServiceFixture {
    public static final long ID = 1L;
    public static final String URL = ContentAnalyzerFixture.URL;
    public static final long LOAD_TIME = ContentAnalyzerFixture.LOAD_TIME;
    public static final String CONTENT = ContentAnalyzerFixture.CONTENT;

    public static final String TITLE = "TITLE";
    public static final String NESTED_URL_1 = "http://host/resource1";
    public static final String NESTED_URL_2 = "http://host/resource2";

    public static IndexRequest request() {
        return IndexRequest.builder()
                .url(URL)
                .content(CONTENT)
                .loadTime(LOAD_TIME)
                .nestedUrls(Set.of(NESTED_URL_1, NESTED_URL_2))
                .build();
    }

    public static Page page() {
        return Page.builder()
                .url(URL)
                .title(TITLE)
                .loadTime(Duration.ofMillis(LOAD_TIME))
                .keywords(keywords())
                .nestedUrls(Set.of(NESTED_URL_1, NESTED_URL_2))
                .build();
    }

    public static Set<Keyword> keywords() {
        return LongStream.range(0 ,10)
                .mapToObj(i -> new Keyword("KEY" + i, i))
                .collect(Collectors.toSet());
    }
}
