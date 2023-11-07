package com.osearch.indexer.domain.valueobject;

import java.util.List;
import java.util.Locale;

/**
 *  A list of supported locales.
 */
public interface SupportedLocales {

    /**
     * Retrieves a list of supported locales.
     *
     * @return A list of Locale objects representing supported locales.
     */
    List<Locale> get();
}
