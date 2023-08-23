package com.osearch.crawler.boot.config;

import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.properties.ApplicationProperties;
import com.osearch.crawler.application.usecase.CrawlerUseCase;
import com.osearch.crawler.application.usecase.CrawlerUseCaseImpl;
import com.osearch.crawler.application.usecase.PageUseCase;
import com.osearch.crawler.application.usecase.PageUseCaseImpl;
import com.osearch.crawler.domain.service.executor.BackgroundExecutor;
import com.osearch.crawler.domain.service.hasher.Hasher;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CrawlerUseCase crawlerUseCase(
        BackgroundExecutor executor,
        HttpService httpService,
        HtmlProcessor processor,
        Hasher hasher,
        PageMessageSender messageSender,
        PageRepository repository,
        ApplicationProperties properties

    ) {
        return new CrawlerUseCaseImpl(executor, httpService, processor,
            hasher, messageSender, repository, properties);
    }

    @Bean
    public PageUseCase pageUseCase(PageRepository repository) {
        return new PageUseCaseImpl(repository);
    }
}
