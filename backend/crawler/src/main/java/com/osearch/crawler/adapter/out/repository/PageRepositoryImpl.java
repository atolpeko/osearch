package com.osearch.crawler.adapter.out.repository;

import com.osearch.crawler.adapter.out.repository.jpa.PageDtoJpaRepository;
import com.osearch.crawler.adapter.out.repository.mapper.PageMapper;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.domain.entity.Page;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository {
    private final PageDtoJpaRepository jpaRepository;
    private final PageMapper mapper;

    @Override
    public Optional<Page> findByUrlHash(String hash) {
        var dto = jpaRepository.findByUrlHash(hash);
        return dto.map(mapper::toEntity);
    }

    @Override
    public void save(Page page) {
        var dto = mapper.toDto(page);
        var saved = jpaRepository.findByUrlHash(page.getUrlHash());
        if (saved.isPresent()) {
            dto = saved.get();
            dto.setHash(page.getHash());
            dto.setFoundAt(page.getFoundAt());
        }

        jpaRepository.save(dto);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
