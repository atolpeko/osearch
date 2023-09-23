package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.entity.AnalyzerContext;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

/**
 * The MainTopicsExtractor class is responsible for extracting the main topics from a document.
 * It analyzes the sentences in a document, extracts the main subjects and actions
 * from the dependency graph of each sentence,
 * and builds a list of Topic objects containing the extracted data.
 */
@Log4j2
public class MainTopicsExtractor extends BaseAnalyzer {

    @Override
    public void analyze(AnalyzerContext context) {
        log.debug("Extracting main topics from {}", context.getId());
        var topics = new HashSet<Topic>();
        for (var sentence : context.getDocument().sentences()) {
            var graph = sentence.dependencyParse();
            var topicsForSentence = extractMainData(graph);
            extractSideData(graph, topicsForSentence);
            topics.addAll(topicsForSentence);
        }


        log.debug("{} main topics extracted from {}", topics.size(), context.getId());
        context.setTopics(topics);
        next(context);
    }

    private List<Topic> extractMainData(SemanticGraph graph) {
        return graph.edgeListSorted().stream()
            .filter(edge -> edge.getRelation().getShortName().equals("nsubj"))
            .map(edge -> Topic.builder()
                .mainSubject(edge.getDependent().word())
                .mainAction(edge.getGovernor().word())
                .build()
            ).collect(Collectors.toList());
    }

    private void extractSideData(SemanticGraph graph, List<Topic> topics) {
        for (var edge : graph.edgeIterable()) {
            var relation = edge.getRelation().getShortName();
            if (relation.equals("compound")) {
                extractCompound(edge, topics);
            } else if (relation.equals("conj")) {
                extractConjunction(edge, topics);
            } else if (relation.equals("nmod:poss")
                || relation.equals("amod")
                || relation.startsWith("numod")) {
                extractAdjective(edge, topics);
            } else if (relation.equals("obj") || relation.equals("obj:in")) {
                extractActionTarget(edge, topics);
            } else if (relation.startsWith("nmod")) {
                extractActionTargetDependent(edge, topics);
            }
        }
    }

    private void extractCompound(SemanticGraphEdge edge, List<Topic> topics) {
        var governor = edge.getGovernor().word();
        var dependent = edge.getDependent().word();
        topics.stream()
            .filter(t -> t.getMainSubject().equals(governor))
            .forEach(t -> t.setMainSubject(dependent + "-" + governor));
    }

    private void extractConjunction(SemanticGraphEdge edge, List<Topic> topics) {
        var governor = edge.getGovernor().word();
        var dependent = edge.getDependent().word();
        topics.stream()
            .filter(t -> t.getMainSubject().equals(governor))
            .forEach(t -> t.setMainSubject(governor + " " + dependent));
    }

    private void extractAdjective(SemanticGraphEdge edge, List<Topic> topics) {
        var governor = edge.getGovernor().word();
        var dependent = edge.getDependent().word();
        topics.stream()
            .filter(t -> t.getMainSubject().equals(governor))
            .forEach(t -> t.setMainAdjective(dependent));
    }

    private void extractActionTarget(SemanticGraphEdge edge, List<Topic> topics) {
        var governor = edge.getGovernor().word();
        var dependent = edge.getDependent().word();
        topics.stream()
            .filter(t -> t.getMainAction().equals(governor))
            .forEach(t -> t.setMainActionTarget(dependent));
    }

    private void extractActionTargetDependent(SemanticGraphEdge edge, List<Topic> topics) {
        var governor = edge.getGovernor().word();
        var dependent = edge.getDependent().word();
        topics.stream()
            .filter(t -> !Objects.isNull(t.getMainActionTarget()))
            .filter(t -> t.getMainActionTarget().equals(governor))
            .forEach(t -> t.setMainActionTarget(dependent + " " + governor));
    }
}
