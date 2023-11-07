package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.valueobject.AnalyzerContext;

import edu.stanford.nlp.coref.data.CorefChain.CorefMention;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

/**
 * The CrossReferenceReplacer class is responsible for replacing cross-references
 * with original mentions in a document.
 */
@Log4j2
@RequiredArgsConstructor
public class CrossReferenceReplacer extends BaseAnalyzer {
    private final StanfordCoreNLP pipeline;

    @Override
    public void analyze(AnalyzerContext context) {
        Callable<Integer> task = () -> {
            log.debug("Replacing cross references for {}", context.getId());
            var replaced = 0;
            var corefChains = context.getDocument().corefChains();
            for (var chain : corefChains.values()) {
                var mentions = chain.getMentionsInTextualOrder();
                if (mentions.size() > 1) {
                    var originalMention = chain.getRepresentativeMention().mentionSpan;
                    for (var mention : mentions) {
                        if (originalMention != null
                            && mention.mentionSpan != null
                            && !originalMention.equals(mention.mentionSpan)) {
                            replace(mention, originalMention, context);
                            replaced++;
                        }
                    }
                }
            }

            context.setDocument(new CoreDocument(context.getContent()));
            pipeline.annotate(context.getDocument());
            return replaced;
        };

        var msg = "Replaced {} cross references in {}";
        withDurationLog(Level.DEBUG, task, msg, context.getId());
        next(context);
    }

    private void replace(CorefMention mention, String name, AnalyzerContext context) {
        var sentence = context.getDocument().sentences().get(mention.sentNum - 1);
        var words = sentence.tokensAsStrings();
        words.subList(mention.headIndex - 1, mention.endIndex - 1).clear();
        words.add(mention.headIndex - 1 , name);

        var updated = String.join(" ", words);
        var newText = context.getContent().replace(sentence.text(), updated);
        context.setContent(newText);
    }
}
