package com.osearch.indexer.boot.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;

import lombok.extern.log4j.Log4j2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("aws")
@Log4j2
public class AwsCredentialsProviderConfig {

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        if (System.getenv("AWS_WEB_IDENTITY_TOKEN_FILE") != null) {
            log.debug("Using AWS WEB WebIdentityTokenCredentialsProvider");
            return WebIdentityTokenCredentialsProvider.builder().build();
        } else {
            log.debug("Using AWS DefaultAWSCredentialsProviderChain");
            return new DefaultAWSCredentialsProviderChain();
        }
    }
}
