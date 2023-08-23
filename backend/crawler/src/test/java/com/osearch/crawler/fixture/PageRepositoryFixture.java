package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.out.repository.dto.PageDto;
import com.osearch.crawler.domain.entity.Page;

import java.time.LocalDateTime;
import java.util.List;

public class PageRepositoryFixture {
    public static final long PAGES_NUMBER = 5L;

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

    public static PageDto pageDto() {
        return PageDto.builder()
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .foundAt(LocalDateTime.now())
            .build();
    }
}
