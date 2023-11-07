package com.osearch.crawler.domain.service.htmlprocessor;

import java.util.List;

/**
 * Used to parse HTML.
 */
public interface HtmlProcessor {

    /**
     * Find nested URLs on HTML page.
     *
     * @param url          page URL.
     * @param htmlContent  page content.
     *
     * @return a list of nested URLS
     */
    List<String> findNestedUrls(String url, String htmlContent);
}
