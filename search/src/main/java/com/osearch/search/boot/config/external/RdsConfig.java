package com.osearch.search.boot.config.external;

import com.osearch.search.boot.config.external.secret.AwsDbSecret;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("aws")
public class RdsConfig {

    @Bean
    public DataSource dataSource(AwsDbSecret awsDbSecret) {
        var url = "jdbc:" + awsDbSecret.getEngine()
            + "://" + awsDbSecret.getHost()
            + ":" + awsDbSecret.getPort()
            + "/" + awsDbSecret.getDbName();
        return DataSourceBuilder.create()
            .url(url)
            .password(awsDbSecret.getPassword())
            .username(awsDbSecret.getUsername())
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build();
    }
}
