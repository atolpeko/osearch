package com.osearch.crawler.domain.service.hasher;

/**
 * Used to hash a string.
 */
public interface Hasher {

    /**
     * Generates a hash value for the given string.
     *
     * @param string the string to be hashed.
     * @return the hash value of the string.
     */
    String hash(String string);
}
