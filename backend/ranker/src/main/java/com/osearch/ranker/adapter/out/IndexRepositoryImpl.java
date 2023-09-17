package com.osearch.ranker.adapter.out;

import com.osearch.ranker.adapter.out.entity.IndexDto;
import com.osearch.ranker.adapter.out.entity.PageDto;
import com.osearch.ranker.adapter.out.jpa.IndexDtoJpaRepository;
import com.osearch.ranker.adapter.out.mapper.IndexMapper;
import com.osearch.ranker.adapter.out.mapper.PageMapper;
import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.util.Optional;

import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndexRepositoryImpl implements IndexRepository {
    private final IndexDtoJpaRepository jpaRepository;
    private final IndexMapper indexMapper;
    private final PageMapper pageMapper;

    @Override
    public Optional<Index> findByKeyword(String keyword) {
        var index = jpaRepository.findByKeywords(keyword);
        return index.map(indexMapper::toEntity);
    }

    @Override
    public void save(Index index) {
        var toSave = indexMapper.toDto(index);
        var pages = toSave.getPages();
        var existing = jpaRepository.findByKeywords(toSave.getKeywords());
        if (existing.isPresent()) {
            toSave = existing.get();
        }

        loadPages(toSave, pages);
        jpaRepository.save(toSave);
    }

    private void loadPages(IndexDto toSave, Set<PageDto> pages) {
        toSave.getPages().clear();
        for (var page: pages) {
            var existing = jpaRepository.getPage(toSave.getKeywords(), page.getUrl());
            if (existing.isPresent()) {
                page = existing.get();
            }

            page.setIndex(toSave);
            toSave.getPages().add(page);
        }
    }

    @Override
    public Optional<Page> getPage(String index, String pageUrl) {
        var page = jpaRepository.getPage(index, pageUrl);
        return page.map(pageMapper::toEntity);
    }
}
