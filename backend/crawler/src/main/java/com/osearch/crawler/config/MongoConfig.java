package com.osearch.crawler.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import com.osearch.crawler.config.properties.MongoProperties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableMongoRepositories(basePackages = "com.osearch.crawler.inout.repository")
@EnableTransactionManagement
@Profile({"debug", "local", "prod"})
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private MongoMappingContext mappingContext;

    @Autowired
    private MongoProperties properties;

    @PostConstruct
    public void init() {
        mappingContext.setAutoIndexCreation(true);
        mappingContext.initialize();
    }

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return properties.getDatabase();
    }

    @Override
    public MongoClient mongoClient() {
        var url = "mongodb://" + properties.getHost() + ":" + properties.getPort();
        return MongoClients.create(url);
    }
}
