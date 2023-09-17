package com.osearch.indexer.boot.config.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osearch.indexer.domain.entity.StopWords;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

@Log4j2
@Configuration
public class StopWordsResource {

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public StopWords stopWords() throws IOException {
        return new StopWords(load());
    }

    private Map<Locale, Set<String>> load() throws IOException {
        var stopWords = new HashMap<Locale, Set<String>>();
        var resources = resourcePatternResolver.getResources("classpath:stopwords/*.json");
        for (var resource: resources) {
            var entry = loadResource(resource);
            log.debug("Stop words {} loaded", resource.getFilename());
            stopWords.put(entry.getKey(), entry.getValue());
        }

        log.debug("Loaded {} stop word files", resources.length);
        return stopWords;
    }

    private Map.Entry<Locale, Set<String>> loadResource(Resource resource) throws IOException {
        var bytes = resource.getInputStream().readAllBytes();
        var jsonContent = new String(bytes, StandardCharsets.UTF_8);
        return parseJson(jsonContent);
    }

    private Map.Entry<Locale, Set<String>> parseJson(String json) throws JsonProcessingException {
        Function<Entry<String, List<String>>, Entry<Locale, Set<String>>> mapper = entry -> {
            var locale = new Locale(entry.getKey());
            var set = new HashSet<>(entry.getValue());

            return new SimpleEntry<>(locale, set);
        };

        var jsonMap = (Map<String, List<String>>) objectMapper.readValue(json, Map.class);
        return jsonMap.entrySet().stream()
            .findFirst()
            .map(mapper)
            .orElseThrow(() -> new RuntimeException("No entry found"));
    }
}
