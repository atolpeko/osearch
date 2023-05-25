package com.osearch.crawler.service.pageprocessor;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.hasher.Hasher;
import com.osearch.crawler.service.rest.RestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageProcessorImpl implements PageProcessor {
    private static final Pattern EXTERNAL_URL_PATTERN =
            Pattern.compile("((http|https)://[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)");

    private final RestService restService;
    private final Hasher hasher;

    @Override
    public URL process(String url) {
        var page = restService.get(url);
        var nestedUrls = findUrls(page);
        return URL.builder()
                .value(url)
                .urlHash(hasher.hash(url))
                .pageHash(hasher.hash(page))
                .nestedUrls(nestedUrls)
                .foundAt(LocalDateTime.now())
                .build();
    }

    private List<String> findUrls(String page) {
        var urls = new ArrayList<String>();
        var matcher = EXTERNAL_URL_PATTERN.matcher(page);
        while (matcher.find()) {
            var url = matcher.group(1);
            urls.add(url);
        }

        return urls;
    }
}
