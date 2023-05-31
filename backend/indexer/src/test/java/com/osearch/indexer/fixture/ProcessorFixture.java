package com.osearch.indexer.fixture;

import com.osearch.indexer.inout.repository.dto.KeywordDto;
import com.osearch.indexer.inout.repository.dto.KeywordRelationDto;
import com.osearch.indexer.inout.repository.dto.PageDto;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ProcessorFixture {
    public static final String URL = ContentAnalyzerFixture.URL;
    public static final long LOAD_TIME = ContentAnalyzerFixture.LOAD_TIME;
    public static final String CONTENT = ContentAnalyzerFixture.CONTENT;

    public static final String TITLE = "TITLE";
    public static final String NESTED_URL_1 = "http://host/resource1";
    public static final String NESTED_URL_2 = "http://host/resource2";

    public static final long SAVED_ID = 1L;

    public static IndexRequest request() {
        return IndexRequest.builder()
                .url(URL)
                .content(CONTENT)
                .loadTime(LOAD_TIME)
                .nestedUrls(List.of(NESTED_URL_1, NESTED_URL_2))
                .build();
    }

    public static Page page() {
        return Page.builder()
                .url(URL)
                .title(TITLE)
                .loadTime(Duration.ofMillis(LOAD_TIME))
                .keywords(keywords())
                .build();
    }

    public static Set<Keyword> keywords() {
        return LongStream.range(0 ,10)
                .mapToObj(i -> new Keyword("KEY" + i, i))
                .collect(Collectors.toSet());
    }

    public static PageDto pageDto() {
        return PageDto.builder()
                .url(URL)
                .loadTime(Duration.ofMillis(LOAD_TIME))
                .title(TITLE)
                .keywordRelations(keywordRelations())
                .build();
    }

    public static Set<KeywordRelationDto> keywordRelations() {
        Function<KeywordDto, KeywordRelationDto> mapper = keyword ->
                KeywordRelationDto.builder()
                        .keyword(keyword)
                        .occurrences(1L)
                        .build();

        return keywordDtos().stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static Set<KeywordDto> keywordDtos() {
        return LongStream.range(0 ,10)
                .mapToObj(i -> KeywordDto.builder().value("KEY_" + i).build())
                .collect(Collectors.toSet());
    }

    public static PageDto savedPageDto() {
        return PageDto.builder()
                .id(SAVED_ID)
                .url(URL)
                .loadTime(Duration.ofMillis(LOAD_TIME))
                .title(TITLE)
                .keywordRelations(keywordRelations())
                .build();
    }

    public static Page nestedPage1() {
        return Page.builder().url(NESTED_URL_1).build();
    }

    public static Page nestedPage2() {
        return Page.builder().url(NESTED_URL_2).build();
    }

    public static PageDto nestedPage1Dto() {
        return PageDto.builder().url(NESTED_URL_1).build();
    }

    public static PageDto nestedPage2Dto() {
        return PageDto.builder().url(NESTED_URL_2).build();
    }
}
