package com.osearch.crawler.domain.service.htmlprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class HtmlProcessorImpl implements HtmlProcessor {
    private final Pattern externalUrlPattern;
    private final Pattern invalidUrlPattern;

    /**
     * Constructs HtmlProcessorImpl.
     *
     * @param externalUrlRegex         external URL regex.
     * @param invalidUrlRegex          invalid URL regex.
     */
    public HtmlProcessorImpl(String externalUrlRegex, String invalidUrlRegex) {
        externalUrlPattern = Pattern.compile(externalUrlRegex);
        invalidUrlPattern = Pattern.compile(invalidUrlRegex);
    }

    @Override
    public List<String> findNestedUrls(String url, String htmlContent) {
        var nestedUrls = findUrls(htmlContent);
        log.debug("Found {} nested URLs from {}", nestedUrls.size(), url);
        nestedUrls = filter(url, nestedUrls);
        log.debug("{} nested URLs from {} left after filtering", nestedUrls.size(), url);

        return nestedUrls;
    }

    private List<String> findUrls(String page) {
        var urls = new ArrayList<String>();
        var matcher = externalUrlPattern.matcher(page);
        while (matcher.find()) {
            var url = matcher.group(1);
            urls.add(url);
        }

        return urls;
    }

    private List<String> filter(String initialUrl, List<String> urls) {
        return urls.stream()
            .filter(url -> !url.equals(initialUrl))
            .filter(invalidUrlPattern.asPredicate().negate())
            .collect(Collectors.toList());
    }
}
