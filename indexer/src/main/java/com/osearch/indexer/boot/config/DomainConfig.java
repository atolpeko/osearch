package com.osearch.indexer.boot.config;

import com.osearch.indexer.domain.IndexService;
import com.osearch.indexer.domain.IndexServiceImpl;
import com.osearch.indexer.domain.analyzer.ContentAnalyzer;
import com.osearch.indexer.domain.analyzer.CrossReferenceReplacer;
import com.osearch.indexer.domain.analyzer.DocumentPreparer;
import com.osearch.indexer.domain.analyzer.MainTopicsExtractor;
import com.osearch.indexer.domain.analyzer.QuotesExtractor;
import com.osearch.indexer.domain.analyzer.SignificanceAnalyzer;
import com.osearch.indexer.domain.analyzer.TopicsAnalyzer;
import com.osearch.indexer.domain.valueobject.SupportedLocales;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public IndexService indexerService(
        SupportedLocales locales,
        ContentAnalyzer documentPreparer,
        ContentAnalyzer crossReferenceReplacer,
        ContentAnalyzer mainTopicsExtractor,
        ContentAnalyzer topicsAnalyzer,
        ContentAnalyzer quotesExtractor,
        ContentAnalyzer significanceAnalyzer
    ) {
        documentPreparer.setNext(crossReferenceReplacer);
        crossReferenceReplacer.setNext(mainTopicsExtractor);
        mainTopicsExtractor.setNext(topicsAnalyzer);
        topicsAnalyzer.setNext(quotesExtractor);
        quotesExtractor.setNext(significanceAnalyzer);

        return new IndexServiceImpl(documentPreparer, locales);
    }

    @Bean
    public ContentAnalyzer documentPreparer(StanfordCoreNLP pipeline) {
        return new DocumentPreparer(pipeline);
    }

    @Bean
    public ContentAnalyzer crossReferenceReplacer(StanfordCoreNLP pipeline) {
        return new CrossReferenceReplacer(pipeline);
    }

    @Bean
    public ContentAnalyzer mainTopicsExtractor() {
        return new MainTopicsExtractor();
    }

    @Bean
    public ContentAnalyzer topicsAnalyzer() {
        return new TopicsAnalyzer();
    }

    @Bean
    public ContentAnalyzer quotesExtractor() {
        return new QuotesExtractor();
    }

    @Bean
    public ContentAnalyzer significanceAnalyzer() {
        return new SignificanceAnalyzer();
    }
}
