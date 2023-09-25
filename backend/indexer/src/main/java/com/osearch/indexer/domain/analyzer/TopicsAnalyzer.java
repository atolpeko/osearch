package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.entity.AnalyzerContext;

import java.util.HashSet;
import java.util.concurrent.Callable;

import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

/**
 * The TopicsAnalyzer class provides functionality to analyze topics in
 * an AnalyzerContext object by extracting additional subtopics
 */
@Log4j2
public class TopicsAnalyzer extends BaseAnalyzer {

    @Override
    public void analyze(AnalyzerContext context) {
        Callable<Integer> task = () -> {
            var topics = context.getTopics();
            log.debug("Extracting additional subtopics from {} main topics in {}",
                topics.size(), context.getId());
            var additional = new HashSet<Topic>();
            for (var topic: topics) {
                var subject = Topic.builder()
                    .mainSubject(topic.getMainSubject())
                    .build();
                additional.add(subject);

                if (topic.getMainAdjective() != null) {
                    var adjSubj = Topic.builder()
                        .mainSubject(topic.getMainSubject())
                        .mainAdjective(topic.getMainAdjective())
                        .build();
                    additional.add(adjSubj);
                }
            }

            topics.addAll(additional);
            return additional.size();
        };

        var msg = "Extracted {} additional subtopics from {}";
        withDurationLog(Level.DEBUG, task, msg, context.getId());
        next(context);
    }
}
