package com.osearch.search.adapter.in.mapper;

import com.osearch.search.domain.entity.IndexInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The IndexInfoMapper class maps a list of IndexInfo objects to a Map with key-value pairs
 * of index and pages count.
 * The returned map preserves the insertion order of elements.
 */
public class IndexInfoMapper {

    /**
     * Maps a list of IndexInfo objects to a Map with key-value pairs
     * of index and pages count.
     * The returned map preserves the insertion order of elements.
     *
     * @param indexInfos the list of IndexInfo objects to be mapped
     * @return a map with key-value pairs of index and pages count
     */
    public Map<String, Integer> map(List<IndexInfo> indexInfos) {
        return indexInfos.stream()
            .collect(Collectors.toMap(
                IndexInfo::getIndex,
                IndexInfo::getPagesCount,
                (existing, replacement) -> existing,
                LinkedHashMap::new
                )
            );
    }
}
