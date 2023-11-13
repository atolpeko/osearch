package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.out.repository.dto.PageDto;
import com.osearch.crawler.domain.entity.Page;

public class PageMongoRepositoryFixture {
    public static final long PAGES_NUMBER = 5L;

    public static final String URL_HASH = "304c42801cd263892249f4d473f4ee72";
    public static final String UPDATED_URL_HASH = "gegrg355555";
    public static final String PAGE_HASH = "300d41ba4d0f3468bf95e319db2c0a85";
    public static final String UPDATED_PAGE_HASH = "gegrg355555";

    public static final Page PAGE =
        Page.builder()
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .build();

    public static final Page UPDATED_PAGE =
        Page.builder()
            .urlHash(UPDATED_URL_HASH)
            .hash(UPDATED_PAGE_HASH)
            .build();

    public static final PageDto PAGE_DTO =
        PageDto.builder()
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .build();

    public static final PageDto UPDATED_PAGE_DTO =
        PageDto.builder()
            .urlHash(UPDATED_URL_HASH)
            .hash(UPDATED_PAGE_HASH)
            .build();
}
