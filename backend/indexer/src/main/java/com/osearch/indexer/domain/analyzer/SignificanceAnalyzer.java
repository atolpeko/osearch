package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.valueobject.Significance;
import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.entity.AnalyzerContext;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

/**
 * The SignificanceAnalyzer class is responsible for analyzing
 * the significance of topics in a document.
 */
@Log4j2
public class SignificanceAnalyzer extends BaseAnalyzer {

    @Override
    public void analyze(AnalyzerContext context) {
        log.debug("Analyzing significance for {} topics in {}",
            context.getTopics().size(), context.getId());
        var occurrenceToTopic = topicsToOccurrences(
            context.getTopics(),
            context.getDocument()
        );

        var total = context.getDocument().sentences().size();
        for (var topic: occurrenceToTopic.entrySet()) {
            var significance = (double) topic.getValue() * 100 / total;
            topic.getKey().setSignificance(Significance.of(significance));
        }

        next(context);
    }

    private Map<Topic, Long> topicsToOccurrences(Set<Topic> topics, CoreDocument document) {
        var occurrenceToTopic = new HashMap<Topic, Long>();
        var sentences = document.sentences();
        for (var topic: topics) {
            var occurrences = countOccurrences(sentences, topic.getMainSubject());
            occurrenceToTopic.put(topic, occurrences);
        }

        return occurrenceToTopic;
    }

    private long countOccurrences(List<CoreSentence> sentences, String subject) {
        var subjectWords = Stream.of(subject.split("[ -]"))
            .map(word -> word.replace("\"", ""))
            .collect(Collectors.toList());
        return sentences.stream()
            .map(CoreSentence::tokensAsStrings)
            .map(words -> words.stream().anyMatch(subjectWords::contains))
            .mapToLong(contains -> contains ? 1: 0)
            .sum();
    }
}
