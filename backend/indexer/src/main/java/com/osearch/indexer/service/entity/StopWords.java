package com.osearch.indexer.service.entity;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StopWords {
    private final Map<Locale, Set<String>> words;

    public Set<String> getForLocale(Locale locale) {
        return words.get(locale);
    }
}
