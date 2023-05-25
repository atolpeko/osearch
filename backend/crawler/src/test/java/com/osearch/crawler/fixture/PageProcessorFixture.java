package com.osearch.crawler.fixture;

import com.osearch.crawler.service.entity.URL;

import java.time.LocalDateTime;
import java.util.List;

public class PageProcessorFixture {
    public static final String URL_STRING = "https://stackoverflow.com/questions/";
    public static final String URL_HASH = "304c42801cd263892249f4d473f4ee72";
    public static final String PAGE_HASH = "300d41ba4d0f3468bf95e319db2c0a85";

    public static final String NESTED_URL_1 = "https://stackoverflow.com/questions/resource.html";
    public static final String NESTED_URL_2 = "http://stackoverflow.com/questions/resource.htm";
    public static final String NESTED_URL_3 = "https://stackoverflow.com/questions/resource.jps";

    public static final String NESTED_URL_4 = "http://stackoverflow.com/questions/resource.php";
    public static final String NESTED_URL_5 = "https://stackoverflow.com/questions/resource.asp";
    public static final String NESTED_URL_6 = "https://stackoverflow.com/questions/resource.aspx";
    public static final String NESTED_URL_7 = "https://stackoverflow.com/questions/resource.cfm";
    public static final String NESTED_URL_8 = "http://stackoverflow.com/questions/resource.cgi";
    public static final String NESTED_URL_9 = "https://stackoverflow.com/questions/resource.shtml";
    public static final String NESTED_URL_10 = "https://stackoverflow.com/questions/resource.xhtml";

    public static final String NESTED_URL_11 = "https://stackoverflow.com/questions/resource.py";
    public static final String NESTED_URL_12 = "http://stackoverflow.com/questions/resource.pl";
    public static final String NESTED_URL_13 = "http://stackoverflow.com/questions/resource.rb";
    public static final String NESTED_URL_14 = "https://stackoverflow.com/questions/resource.jspx";
    public static final String NESTED_URL_15 = "https://stackoverflow.com/questions/resource.action";
    public static final String NESTED_URL_16 = "https://stackoverflow.com/questions/resource.cfml";
    public static final String NESTED_URL_17 = "https://stackoverflow.com/questions/resource.tpl";
    public static final String NESTED_URL_18 = "https://stackoverflow.com/questions/resource.tmpl";

    public static final String NESTED_URL_19 = "http://stackoverflow.com/questions/resource";
    public static final String NESTED_URL_20 = "https://stackoverflow.com/questions/resource/";

    public static final String IGNORED_NESTED_URL_1 = "https://stackoverflow.com/questions/resource.jpg";
    public static final String IGNORED_NESTED_URL_2 = "http://stackoverflow.com/questions/resource.png";
    public static final String IGNORED_NESTED_URL_3 = URL_STRING;

    public static final String PAGE =
            "<!doctype html>\n " +
                "<html>\n " +
                    "<head></head>\n " +
                        "<body>\n " +
                            "<h1>Hello</h1>\n " +
                            "<a href=" + NESTED_URL_1 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_2 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_3 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_4+ ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_5 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_6 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_7 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_8 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_9 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_10 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_11 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_12 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_13 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_14 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_15 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_16 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_17 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_18 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_19 + ">Nested</a>\n " +
                            "<a href=" + NESTED_URL_20 + ">Nested</a>\n " +
                            "<a href=" + IGNORED_NESTED_URL_1 + ">Ignored</a>\n " +
                            "<a href=" + IGNORED_NESTED_URL_2 + ">Ignored</a>\n " +
                            "<a href=" + IGNORED_NESTED_URL_3 + ">Ignored</a>\n " +
                        "</body>\n " +
                "</html>";


    public static URL url() {
        var nestedUrls = List.of(
                NESTED_URL_1, NESTED_URL_2, NESTED_URL_3, NESTED_URL_4,
                NESTED_URL_5, NESTED_URL_6, NESTED_URL_7, NESTED_URL_8,
                NESTED_URL_9, NESTED_URL_10, NESTED_URL_11, NESTED_URL_12,
                NESTED_URL_13, NESTED_URL_14, NESTED_URL_15, NESTED_URL_16,
                NESTED_URL_17, NESTED_URL_18, NESTED_URL_19, NESTED_URL_20
        );
        return URL.builder()
                .value(URL_STRING)
                .urlHash(URL_HASH)
                .pageHash(PAGE_HASH)
                .nestedUrls(nestedUrls)
                .foundAt(LocalDateTime.now())
                .build();
    }
}
