package com.osearch.crawler.service.pageprocessor;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.hasher.Hasher;
import com.osearch.crawler.service.http.HttpService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class PageProcessorImpl implements PageProcessor {
    private static final Pattern EXTERNAL_URL_PATTERN;
    private static final Pattern NOT_HTML_PATTERN;

    private final HttpService httpService;
    private final Hasher hasher;

    static {
        var urlRegex = "((http|https)://[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        var noHtmlRegex = ".*\\.(js|txt|json|csv|pdf|xml|mp3|mp4|avi|mov|zip|rar|exe|jpg|jpeg|gif|png|svg)$";

        EXTERNAL_URL_PATTERN = Pattern.compile(urlRegex);
        NOT_HTML_PATTERN = Pattern.compile(noHtmlRegex);
    }

    @Override
    public URL process(String url) {
        var response = httpService.get(url);
        var nestedUrls = findUrls(response.getContent());
        log.debug("Found {} nested URLs from {}", nestedUrls.size(), url);
        nestedUrls = filter(url, nestedUrls);
        log.debug("{} nested URLs from {} left after filtering", nestedUrls.size(), url);

        return URL.builder()
                .value(url)
                .content(response.getContent())
                .urlHash(hasher.hash(url))
                .pageHash(hasher.hash(response.getContent()))
                .loadTime(response.getLoadTime())
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

    private List<String> filter(String initialUrl, List<String> urls) {
        return urls.stream()
                .filter(url -> !url.equals(initialUrl))
                .filter(NOT_HTML_PATTERN.asPredicate().negate())
                .collect(Collectors.toList());
    }
}
