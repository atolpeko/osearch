package com.osearch.indexer.boot.config.external;

import com.osearch.indexer.boot.config.properties.NeptuneProperties;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Config.TrustStrategy;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("aws")
public class NeptuneConfig {

    @Bean
    public Driver readDriver(NeptuneProperties properties) {
        return GraphDatabase.driver(
            properties.getReadEndpoint(),
            AuthTokens.none(),
            Config.builder().withEncryption()
                .withTrustStrategy(TrustStrategy.trustSystemCertificates())
                .build()
        );
    }

    @Bean
    public Driver writeDriver(NeptuneProperties properties) {
        return GraphDatabase.driver(
            properties.getWriteEndpoint(),
            AuthTokens.none(),
            Config.builder().withEncryption()
                .withTrustStrategy(TrustStrategy.trustSystemCertificates())
                .build()
        );
    }
}
