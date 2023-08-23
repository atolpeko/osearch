package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.entity.IndexRequest;
import com.osearch.indexer.domain.entity.Keyword;
import com.osearch.indexer.domain.entity.Page;
import com.osearch.indexer.domain.entity.StopWords;

import java.text.BreakIterator;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

@Log4j2
@RequiredArgsConstructor
public class ContentAnalyzerImpl implements ContentAnalyzer {
    private final StopWords stopWords;

    @Override
    public Page analyze(IndexRequest request) {
        log.debug("Analyzing request with URL: {}", request.getUrl());
        var document = Jsoup.parse(request.getContent());
        var locale = getLocale(document);

        return Page.builder()
            .url(request.getUrl())
            .loadTime(Duration.ofMillis(request.getLoadTime()))
            .title(getPageTitle(document))
            .keywords(getKeywords(document, locale))
            .build();
    }

    private String getPageTitle(Document document) {
        return document.title();
    }

    private Locale getLocale(Document document) {
        var localeInHtml = Optional.ofNullable(document.selectFirst("html"))
            .map(element -> element.attr("lang"))
            .map(Locale::new);
        if (localeInHtml.isPresent()) {
            return localeInHtml.get();
        }

        var localeInMeta = Optional.ofNullable(document
                .select("meta[name=language], meta[http-equiv=content-language]")
                .first())
                .map(element -> element.attr("content"))
                .map(Locale::new);
        if (localeInMeta.isPresent()) {
            return localeInMeta.get();
        }

        log.debug("No locale found. Setting default ENG");
        return Locale.ENGLISH;
    }

    public Set<Keyword> getKeywords(Document document, Locale locale) {
        var text = document.text() + metaToString(getMeta(document));
        var keywords = findKeywords(text, locale);

        return keywords.stream()
            .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
            .entrySet()
            .parallelStream()
            .map(entry -> new Keyword(entry.getKey(), entry.getValue()))
            .collect(Collectors.toSet());
    }

    private List<Element> getMeta(Document document) {
        var metaTags = document.select("meta");
        return new ArrayList<>(metaTags);
    }

    private List<String> findKeywords(String text, Locale locale) {
        var keywords = new ArrayList<String>();

        var wordIterator = BreakIterator.getWordInstance(locale);
        wordIterator.setText(text);
        var start = wordIterator.first();
        var end = wordIterator.next();

        while (end != BreakIterator.DONE) {
            var keyword = text.substring(start, end).toLowerCase();
            if (isNotStopWord(keyword, locale) && keyword.length() >= 2) {
                keywords.add(keyword);
            }

            start = end;
            end = wordIterator.next();
        }

        return keywords;
    }

    private String metaToString(List<Element> meta) {
        return  " " + meta.stream()
            .filter(metaElement -> metaElement.attr("name").equals("keywords") ||
                metaElement.attr("name").equals("description"))
            .map(metaElement -> metaElement.attr("content"))
            .filter(str -> !str.isEmpty())
            .map(str -> str + " ")
            .collect(Collectors.joining());
    }

    private boolean isNotStopWord(String keyword, Locale locale) {
        var words = stopWords.getForLocale(locale);
        return !words.contains(keyword);
    }
}
