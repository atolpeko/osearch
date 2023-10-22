package com.osearch.crawler.boot.config;

import com.osearch.crawler.adapter.in.messaging.validator.RequestValidator;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public RequestValidator requestValidator() {
        return new RequestValidator();
    }
}
