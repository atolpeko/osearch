package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.valueobject.AnalyzerContext;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

/**
 * The DocumentPreparer class is responsible for annotating a document
 * using the StanfordCoreNLP pipeline.
 */
@Log4j2
@RequiredArgsConstructor
public class DocumentPreparer extends BaseAnalyzer {
    private final StanfordCoreNLP pipeline;

    @Override
    public void analyze(AnalyzerContext context) {
        Runnable task = () -> {
            log.debug("Preparing document for {}", context.getId());
            var document = new CoreDocument(context.getContent());
            pipeline.annotate(document);
            context.setDocument(document);
        };

        var msg = "Prepared document for {}";
        withDurationLog(Level.DEBUG, task, msg, context.getId());
        next(context);
    }
}
