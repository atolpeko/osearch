package com.osearch.indexer.boot.config;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreNlpConfig {

    @Bean
    public StanfordCoreNLP pipeline() {
        var props = new Properties();
        props.setProperty(
            "annotators",
            "tokenize,ssplit,pos,lemma,ner,depparse,coref,quote"
        );

        return new StanfordCoreNLP(props);
    }
}
