package com.osearch.crawler.domain.service.hasher;

/**
 * Used to hash a string.
 */
public interface Hasher {

    /**
     * Hash a string.
     *
     * @param string  string to hash
     *
     * @return hash
     */
    String hash(String string);
}
