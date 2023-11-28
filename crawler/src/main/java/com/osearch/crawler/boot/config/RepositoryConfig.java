package com.osearch.crawler.boot.config;

import com.osearch.crawler.adapter.out.repository.PageDynamoDbRepository;
import com.osearch.crawler.adapter.out.repository.PageMongoRepository;
import com.osearch.crawler.adapter.out.repository.jpa.PageDtoJpaRepository;
import com.osearch.crawler.adapter.out.repository.mapper.PageDynamoDbMapper;
import com.osearch.crawler.adapter.out.repository.mapper.PageJpaMapper;
import com.osearch.crawler.adapter.out.repository.properties.DynamoDbRepositoryProperties;
import com.osearch.crawler.application.port.PageRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class RepositoryConfig {

    @Bean
    @Profile("aws")
    public PageRepository pageDynamoDbRepository(
        DynamoDbClient client,
        PageDynamoDbMapper mapper,
        DynamoDbRepositoryProperties properties
    ) {
        return new PageDynamoDbRepository(client, mapper, properties);
    }

    @Bean
    @Profile({"test", "debug", "local", "prod"})
    public PageRepository pageMongoRepository(
        PageDtoJpaRepository jpaRepository,
        PageJpaMapper mapper
    ) {
        return new PageMongoRepository(jpaRepository, mapper);
    }
}
