package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.port.exception.DataAccessException;
import com.osearch.crawler.application.usecase.exception.PageUseCaseException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageUseCaseImpl implements PageUseCase {
    private final PageRepository repository;

    @Override
    public long countCrawled() {
        try {
            return repository.count();
        } catch (DataAccessException e) {
            var msg = "Database not available: " + e.getMessage();
            throw new PageUseCaseException(msg, e);
        } catch (Exception e) {
            throw new PageUseCaseException(e.getMessage(), e);
        }
    }
}
