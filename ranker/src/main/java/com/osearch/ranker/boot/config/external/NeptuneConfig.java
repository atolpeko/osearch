package com.osearch.ranker.boot.config.external;

import com.osearch.ranker.boot.config.properties.NeptuneProperties;

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
    public Driver driver(NeptuneProperties properties) {
        return GraphDatabase.driver(
            properties.getReadEndpoint(),
            AuthTokens.none(),
            Config.builder().withEncryption()
                .withTrustStrategy(TrustStrategy.trustSystemCertificates())
                .build()
        );
    }
}
