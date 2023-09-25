package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.entity.AnalyzerContext;

import java.util.concurrent.Callable;

import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

/**
 * The QuotesExtractor class is responsible for extracting quotes
 * from a document and adding them as topics to the context.
 */
@Log4j2
public class QuotesExtractor extends BaseAnalyzer {

    @Override
    public void analyze(AnalyzerContext context) {
        Callable<Integer> task = () -> {
            log.debug("Extracting quotes from {}", context.getId());
            var quotes = context.getDocument().quotes();
            for (var quote: quotes) {
                var topic = Topic.builder()
                    .mainSubject(quote.text())
                    .build();
                context.getTopics().add(topic);
            }

            return quotes.size();
        };

        var msg = "Extracted {} quotes from {}";
        withDurationLog(Level.DEBUG, task, msg, context.getId());
        next(context);
    }
}
