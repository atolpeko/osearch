package com.osearch.search.adapter.out;

import com.osearch.search.application.port.IndexRepository;
import com.osearch.search.application.port.exception.DataAccessException;
import com.osearch.search.domain.entity.IndexInfo;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class IndexRepositoryImpl implements IndexRepository {
    private final JdbcTemplate jdbc;

    private final RowMapper<IndexInfo> mapper = (resultSet, rowNum) ->
        IndexInfo.builder()
            .index(resultSet.getString("topic"))
            .pagesCount(resultSet.getInt("c"))
            .build();

    @Override
    public List<IndexInfo> findInfo(int offset, int limit) {
        try {
            var query = "SELECT i.topic, COUNT(p.id) AS c "
                + "FROM indexes i "
                + "LEFT JOIN pages p ON p.index_key = i.id "
                + "GROUP BY i.topic "
                + "ORDER BY c DESC "
                + "LIMIT ? OFFSET ?";

            return jdbc.query(query, mapper, limit, offset);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
