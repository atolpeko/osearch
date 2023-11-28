package com.osearch.search.boot.config.external.secret;

import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.search.boot.config.properties.AwsSecretProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("aws")
@RequiredArgsConstructor
public class AwsSecretConfig {
    private final ObjectMapper mapper;

    @Bean
    public AwsDbSecret awsDbSecret(AwsSecretProperties properties) {
        var secret = "";
        try {
            var client = AWSSecretsManagerClientBuilder
                .standard()
                .withRegion(properties.getRegion())
                .build();
            var getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(properties.getName());
            var getSecretValueResult = client.getSecretValue(getSecretValueRequest);

            secret = getSecretValueResult.getSecretString();
            return mapper.readValue(secret, AwsDbSecret.class);
        } catch (ResourceNotFoundException | InvalidRequestException e) {
            var msg = "Can't load RDS secret: " + secret + " is not present";
            throw new RuntimeException(msg, e);
        } catch (JsonParseException | JsonMappingException e) {
            var msg = "Can't load RDS secret: " + secret + " is invalid";
            throw new RuntimeException(msg, e);
        } catch (Exception e) {
            var msg = "Can't load RDS secret: " + e.getMessage();
            throw new RuntimeException(msg, e);
        }
    }
}
