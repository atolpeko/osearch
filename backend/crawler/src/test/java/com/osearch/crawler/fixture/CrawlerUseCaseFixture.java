package com.osearch.crawler.fixture;

import java.util.List;

public class CrawlerUseCaseFixture {

    public static List<String> initialUrls() {
        return List.of(
            "https://www.youtube.com",
            "https://www.baeldung.com/"
        );
    }
}
