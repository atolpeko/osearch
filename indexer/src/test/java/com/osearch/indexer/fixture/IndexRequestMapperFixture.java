package com.osearch.indexer.fixture;

import com.osearch.indexer.domain.valueobject.IndexRequest;

public class IndexRequestMapperFixture {
    public static final String URL = "https://host/resource";
    public static final long LOAD_TIME = 1000L;
    public static final String CONTENT =
        "<!doctype html>\n" +
            "<html lang=\"en\">\n " +
            "<head> " +
                "<meta charset=\"UTF-8'\"> " +
                "<meta name=\"author\" content=\"John Doe\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>My Travel Blog</title>" +
            "</head>\n " +
            "<body>\n " +
                "<h1>Hello! This is a travel blog. Today I'm gonna show you Spain</h1>\n " +
                "<p>Holiday vibes are a way of life in beautiful Spain!</p> " +
            "</body>\n " +
        "</html>";

    public static final IndexRequest REQUEST =
        IndexRequest.builder()
            .url(URL)
            .content(CONTENT)
            .loadTime(LOAD_TIME)
            .build();

    public static final String JSON = "{ \"url\":\"" + URL + "\", "
        + "\"content\":\"" + CONTENT + "\", "
        + "\"loadTime\":\"" + LOAD_TIME + "\" }";

    public static final String INVALID_JSON = "{1}";
}
