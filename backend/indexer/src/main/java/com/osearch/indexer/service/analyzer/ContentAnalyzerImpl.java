package com.osearch.indexer.service.analyzer;

import com.osearch.indexer.service.entity.HTMLElement;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;
import com.osearch.indexer.service.entity.StopWords;

import java.text.BreakIterator;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class ContentAnalyzerImpl implements ContentAnalyzer {
    private final StopWords stopWords;

    @Override
    public Page analyze(IndexRequest request) {
        log.debug("Analyzing request with URL: {}", request.getUrl());
        var document = Jsoup.parse(request.getContent());
        var meta = getMeta(document);
        var locale = getLocale(document);

        return Page.builder()
                .url(request.getUrl())
                .loadTime(Duration.ofMillis(request.getLoadTime()))
                .title(getPageTitle(document))
                .metaTags(meta)
                .keywords(getKeywords(document, locale, meta))
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

    private Set<HTMLElement> getMeta(Document document) {
        var meta = new HashSet<HTMLElement>();
        var metaTags = document.select("meta");
        for (var tag : metaTags) {
            var name = tag.attr("name");
            var content = tag.attr("content");
            meta.add(new HTMLElement(name, content));
        }

        meta.removeIf(tag -> tag.getKey().isEmpty());
        meta.removeIf(tag -> tag.getValue().isEmpty());
        return meta;
    }

    public Set<Keyword> getKeywords(Document document, Locale locale, Set<HTMLElement> meta) {
        var text = document.text() + metaToString(meta);
        var keywords = findKeywords(text, locale);

        return keywords.stream()
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> new Keyword(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
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

    private String metaToString(Set<HTMLElement> meta) {
         return " " + meta.stream()
                 .filter(metaElement -> metaElement.getKey().equals("keywords") ||
                         metaElement.getKey().equals("description"))
                 .map(HTMLElement::getValue)
                 .map(str -> str + " ")
                 .collect(Collectors.joining());
    }

    private boolean isNotStopWord(String keyword, Locale locale) {
        var words = stopWords.getForLocale(locale);
        return !words.contains(keyword);
    }
}
