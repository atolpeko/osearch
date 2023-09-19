package com.osearch.crawler.fixture;

import com.osearch.crawler.adapter.out.http.HttpResponseEntity;
import com.osearch.crawler.application.port.entity.HttpResponse;

import java.net.URI;
import java.time.Duration;

public class HttpServiceFixture {
    public static final String URL = "https://stackoverflow.com/questions/";
    public static final Integer MAX_REDIRECTS = 3;
    public static final String REDIRECT_URL = "https://stackoverflow.com/questions/1";
    public static final URI REDIRECT_URI = URI.create(REDIRECT_URL);
    public static final Duration LOAD_TIME = Duration.ofMillis(1000);

    public static final String HTML =
            "<!doctype html>\n" +
                "<html>\n " +
                    "<head></head>\n " +
                    "<body>\n " +
                        "<h1>Hello</h1>\n " +
                    "</body>\n " +
                "</html>";

    public static final String JSON = "{ }";

    public static HttpResponse RESPONSE =
        HttpResponseEntity.builder()
            .url(URL)
            .content(HTML)
            .loadTime(LOAD_TIME)
            .build();
}
