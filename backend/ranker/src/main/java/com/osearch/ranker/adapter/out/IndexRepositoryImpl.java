package com.osearch.ranker.adapter.out;

import com.osearch.ranker.adapter.out.jpa.IndexDtoJpaRepository;
import com.osearch.ranker.adapter.out.mapper.IndexMapper;
import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.domain.entity.Index;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndexRepositoryImpl implements IndexRepository {
    private final IndexDtoJpaRepository jpaRepository;
    private final IndexMapper mapper;

    @Override
    public Optional<Index> findByKeyword(String keyword) {
        var index = jpaRepository.findByKeywords(keyword);
        return index.map(mapper::toEntity);
    }

    @Override
    public void save(Index index) {
        var dto = mapper.toDto(index);
        jpaRepository.save(dto);
    }
}
