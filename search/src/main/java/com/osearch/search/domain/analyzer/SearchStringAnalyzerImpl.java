package com.osearch.search.domain.analyzer;

import com.osearch.search.domain.valueobject.Topics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class SearchStringAnalyzerImpl implements SearchStringAnalyzer {

    @Override
    public Topics analyze(String searchString) {
        var search = prepareString(searchString);
        var words = search.split(" ");
        var combinations = generateWordCombinations(Arrays.asList(words)).stream()
            .filter(list -> !list.isEmpty())
            .map(list -> String.join(" ", list))
            .filter(string -> !string.equals(search))
            .sorted(Comparator.comparing(String::length).reversed())
            .collect(Collectors.toList());

        return Topics.builder()
            .mainTopic(search)
            .sideTopics(combinations)
            .build();
    }

    private String prepareString(String searchString) {
        return searchString.replace(".", " ")
            .replace("\n", "");
    }

    public List<List<String>> generateWordCombinations(List<String> words) {
        var combinations = new ArrayList<List<String>>();
        generateCombinations(words, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    private void generateCombinations(
        List<String> words,
        int index,
        List<String> current,
        List<List<String>> combinations)
    {
        if (index == words.size()) {
            combinations.add(new ArrayList<>(current));
            return;
        }

        current.add(words.get(index));
        generateCombinations(words, index + 1, current, combinations);

        current.remove(current.size() - 1);
        generateCombinations(words, index + 1, current, combinations);
    }
}
