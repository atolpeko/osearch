package com.osearch.crawler.config;

import com.osearch.crawler.boot.CrawlerApplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(classes = CrawlerApplication.class)
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = "request")
@EnableMongoRepositories(basePackages = "com.osearch.crawler.adapter.out.repository")
public @interface IntegrationTest {

}
