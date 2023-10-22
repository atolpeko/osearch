package com.osearch.search.application.entity;

import java.util.List;

import lombok.Data;

/**
 * A class representing a pageable list.
 *
 * @param <T> the type of the value contained in the Pageable list
 */
@Data
public class Pageable<T> {
    private final List<T> values;
    private final boolean hasNextPage;

    /**
     * Creates a new instance of the Pageable class with the specified
     * value and hasNextPage flag.
     *
     * @param values A list of values associated with the page
     * @param hasNextPage A flag indicating whether there is a next page or not
     * @param <T> The type of the value
     *
     * @return A new instance of the Pageable class
     */
    public static<T> Pageable<T> of(List<T> values, boolean hasNextPage) {
        return new Pageable<>(values, hasNextPage);
    }

    private Pageable(List<T> values, boolean hasNextPage) {
        this.values = values;
        this.hasNextPage = hasNextPage;
    }
}
