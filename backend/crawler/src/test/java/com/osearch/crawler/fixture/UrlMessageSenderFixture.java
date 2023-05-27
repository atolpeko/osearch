package com.osearch.crawler.fixture;

import com.osearch.crawler.inout.messaging.entity.URLDto;
import com.osearch.crawler.inout.messaging.entity.URLPackDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UrlMessageSenderFixture {
    public static final String TOPIC = "TOPIC";
    public static final int BULK_COUNT = 2;

    public static URLDto url(int number) {
        return new URLDto("URL_" + number);
    }

    public static URLPackDto urlPack() {
        return new URLPackDto(generateUrls());
    }

    private static List<URLDto> generateUrls() {
        return IntStream.range(0, BULK_COUNT)
                .mapToObj(UrlMessageSenderFixture::url)
                .collect(Collectors.toList());
    }

    public static String urlPackJson() {
        var pack = urlPack();
        var urls = pack.getUrls().stream()
                .map(URLDto::getValue)
                .map(str -> "{ \"value\": \"" + str + "\" }")
                .collect(Collectors.toList());
        var urlsJson = String.join(", ", urls);

        return "{ \"urls\": ["  + urlsJson + "]}";
    }
}
