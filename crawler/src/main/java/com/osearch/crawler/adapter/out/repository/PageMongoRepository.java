package com.osearch.crawler.adapter.out.repository;

import com.osearch.crawler.adapter.out.repository.jpa.PageDtoJpaRepository;
import com.osearch.crawler.adapter.out.repository.mapper.PageJpaMapper;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.port.exception.DataAccessException;
import com.osearch.crawler.domain.entity.Page;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageMongoRepository implements PageRepository {
    private final PageDtoJpaRepository jpaRepository;
    private final PageJpaMapper mapper;

    @Override
    public Optional<Page> findByUrlHash(String hash) {
        try {
            var dto = jpaRepository.findByUrlHash(hash);
            return dto.map(mapper::toEntity);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Page page) {
        try {
            var dto = mapper.toDto(page);
            var saved = jpaRepository.findByUrlHash(page.getUrlHash());
            if (saved.isPresent()) {
                dto = saved.get();
                dto.setHash(page.getHash());
                dto.setFoundAt(page.getFoundAt());
            }

            jpaRepository.save(dto);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public long count() {
        try {
            return jpaRepository.count();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
