package com.osearch.search.application.usecase;

import com.osearch.search.application.entity.Pageable;
import com.osearch.search.application.port.IndexRepository;
import com.osearch.search.application.port.exception.DataAccessException;
import com.osearch.search.application.usecase.exception.UseCaseException;
import com.osearch.search.domain.entity.IndexInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndexUseCaseImpl implements IndexUseCase {
    private final IndexRepository repository;

    @Override
    public Pageable<IndexInfo> getInfo(int offset, int limit) {
        try {
            var result = repository.findInfo(offset, limit + 1);
            var hasNext = result.size() == limit + 1;
            if (hasNext) {
                result = result.subList(0, result.size() - 1);
            }

            return Pageable.of(result,  hasNext);
        } catch (DataAccessException e) {
            throw new UseCaseException("DB not available: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new UseCaseException(e.getMessage(), e);
        }
    }
}
