package com.osearch.search.adapter.out;

import com.osearch.search.application.port.PageRepository;
import com.osearch.search.application.port.exception.DataAccessException;
import com.osearch.search.domain.entity.Page;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository {
    private final JdbcTemplate jdbc;

    private final RowMapper<Page> pageMapper = (resultSet, rowNum) ->
        Page.builder()
            .url(resultSet.getString("url"))
            .title(resultSet.getString("title"))
            .build();

    @Override
    public List<Page> findAllByTopic(String topic, int offset, int limit) {
        try {
            var query = "SELECT p.url, p.title "
                + "FROM indexes i "
                + "LEFT JOIN pages p ON p.index_key = i.id "
                + "WHERE i.topic = ? "
                + "ORDER BY p.page_rank DESC "
                + "LIMIT ? OFFSET ?";

            return jdbc.query(query, pageMapper, topic, limit, offset);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public int countForTopic(String topic) {
        try {
            var query = "SELECT COUNT(p.id) AS c "
                + "FROM indexes i "
                + "LEFT JOIN pages p ON p.index_key = i.id "
                + "WHERE i.topic = ? ";

            RowMapper<Integer> mapper = (resultSet, rowNum) -> resultSet.getInt("c");
            return jdbc.queryForObject(query, mapper, topic);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
