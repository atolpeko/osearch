package com.osearch.indexer.fixture;

import com.osearch.indexer.domain.entity.Page;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class PageMessageSenderFixture {
    public static final String TOPIC = "TOPIC";

    public static final String URL = "https://stackoverflow.com/questions/1.html";
    public static final Long LOAD_TIME = 1000L;
    public static final Set<String> NESTED_URLS = Set.of(
        "http://stackoverflow.com/questions/2.htm",
        "https://stackoverflow.com/questions/3.jps"
    );
    public static final String JSON = "{" + URL + "}";

    public static Page page() {
        return Page.builder()
            .url(URL)
            .nestedUrls(NESTED_URLS)
            .loadTime(Duration.of(LOAD_TIME, ChronoUnit.MILLIS))
            .build();
    }
}
