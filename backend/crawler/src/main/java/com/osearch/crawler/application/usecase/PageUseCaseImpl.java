package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.usecase.exception.PageServiceException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageUseCaseImpl implements PageUseCase {
    private final PageRepository repository;

    @Override
    public long countProcessed() {
        try {
            return repository.count();
        } catch (Exception e) {
            throw new PageServiceException(e.getMessage(), e);
        }
    }
}
