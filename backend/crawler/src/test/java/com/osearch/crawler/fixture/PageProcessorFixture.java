package com.osearch.crawler.fixture;

import com.osearch.crawler.service.entity.URL;

import java.time.LocalDateTime;
import java.util.List;

public class PageProcessorFixture {
    public static final String URL_STRING = "https://stackoverflow.com/questions/";
    public static final String URL_HASH = "304c42801cd263892249f4d473f4ee72";
    public static final String PAGE_HASH = "300d41ba4d0f3468bf95e319db2c0a85";
    public static final String NESTED_URL_1 = "https://stackoverflow.com/questions/1";
    public static final String NESTED_URL_2 = "https://stackoverflow.com/questions/2";
    public static final String NESTED_URL_3 = "https://stackoverflow.com/questions/3";

    public static final String PAGE =
            "<!doctype html>\n " +
                "<html>\n " +
                    "<head></head>\n " +
                        "<body>\n " +
                            "<h1>Hello</h1>\n " +
                            "<a href=" + NESTED_URL_1 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_2 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_3 + ">Nested</a>\n " +
                        "</body>\n " +
                "</html>";


    public static URL url() {
        return URL.builder()
                .value(URL_STRING)
                .urlHash(URL_HASH)
                .pageHash(PAGE_HASH)
                .nestedUrls(List.of(NESTED_URL_1, NESTED_URL_2, NESTED_URL_3))
                .foundAt(LocalDateTime.now())
                .build();
    }
}
