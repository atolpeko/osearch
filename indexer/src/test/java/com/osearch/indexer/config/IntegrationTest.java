package com.osearch.indexer.config;

import com.osearch.indexer.boot.IndexerApplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(classes = IndexerApplication.class)
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = "request")
@AutoConfigureMockMvc
public @interface IntegrationTest {

}
