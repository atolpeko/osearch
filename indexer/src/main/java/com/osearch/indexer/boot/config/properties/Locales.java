package com.osearch.indexer.boot.config.properties;

import com.osearch.indexer.domain.valueobject.SupportedLocales;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Locales implements SupportedLocales {

    @Value("#{'${domain.supportedLocales}'.split(',')}")
    private List<String> localeList;

    @Override
    public List<Locale> get() {
        return localeList.stream()
            .map(Locale::forLanguageTag)
            .collect(Collectors.toList());
    }
}
