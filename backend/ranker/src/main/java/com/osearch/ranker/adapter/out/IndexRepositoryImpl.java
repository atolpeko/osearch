package com.osearch.ranker.adapter.out;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.application.port.exception.DataModificationException;
import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Transactional
@RequiredArgsConstructor
public class IndexRepositoryImpl implements IndexRepository {
    private final JdbcTemplate jdbc;

    private final RowMapper<Page> pageMapper = (resultSet, rowNum) ->
        Page.builder()
            .sourceId(resultSet.getLong("source_id"))
            .url(resultSet.getString("url"))
            .pageRank(resultSet.getDouble("page_rank"))
            .build();

    @Override
    public Optional<Index> findByKeyword(String keyword) {
        try {
            var query = "SELECT p.source_id, p.url, p.page_rank "
                + "FROM indexes i "
                + "LEFT JOIN pages p ON p.index_key = i.id "
                + "WHERE i.keywords = ?";
            var pages = jdbc.query(query, pageMapper, keyword);

            var index = Index.builder()
                .keywords(keyword)
                .pages(new HashSet<>(pages))
                .build();
            return Optional.of(index);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Index index) {
        try {
            var id = saveIndex(index);
            savePages(index.getPages(), id);
        } catch (DuplicateKeyException e) {
            throw new DataModificationException(e.getMessage(), e);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    private long saveIndex(Index index) {
        var savedId = findIndex(index);
        if (savedId == null) {
            log.debug("Saving new index: {}", index.getKeywords());
            var statementFactory = new PreparedStatementCreatorFactory(
                "INSERT INTO indexes (keywords) VALUES (?)", Types.VARCHAR
            );
            statementFactory.setReturnGeneratedKeys(true);
            var statementCreator = statementFactory.newPreparedStatementCreator(
                List.of(index.getKeywords())
            );
            var keyHolder = new GeneratedKeyHolder();
            jdbc.update(statementCreator, keyHolder);
            savedId = keyHolder.getKey().longValue();
        } else {
            log.debug("Index already exists: {}", index.getKeywords());
        }

        return savedId;
    }

    private Long findIndex(Index index) {
        var query = "SELECT i.id FROM indexes i WHERE i.keywords = ?";
        RowMapper<Long> rowMapper = (rs, rowNum) -> rs.getLong("id");
        return jdbc.queryForObject(query, rowMapper, index.getKeywords());
    }

    private void savePages(Set<Page> pages, long indexId) {
        var query = "INSERT INTO pages (source_id, url, page_rank, index_key) "
            + "VALUES (?, ?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE page_rank = ?";

        var batchArgs = new ArrayList<Object[]>();
        for (var page: pages) {
            var values = new Object[]{
                page.getSourceId(),
                page.getUrl(),
                page.getPageRank(),
                indexId,
                page.getPageRank()};
            batchArgs.add(values);
        }

        log.debug("Saving {} pages", pages.size());
        jdbc.batchUpdate(query, batchArgs);
    }

    @Override
    public Optional<Page> getPage(String index, String pageUrl) {
        try {
            var query = "SELECT p.source_id, p.url, p.page_rank "
                + "FROM indexes i "
                + "LEFT JOIN pages p ON p.index_key = i.id "
                + "WHERE i.keywords = ? AND p.url = ?";

            var page = jdbc.queryForObject(query, pageMapper, index, pageUrl);
            return Optional.of(page);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
