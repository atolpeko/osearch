package com.osearch.crawler.domain.service.hasher;

import static com.osearch.crawler.fixture.HasherFixture.VALUE;
import static com.osearch.crawler.fixture.HasherFixture.HASH;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class SHA256HasherTest {
    SHA256Hasher target;

    @BeforeEach
    void setUp() {
        target = new SHA256Hasher();
    }

    @Test
    void shouldHash() {
        var hash = target.hash(VALUE);
        assertEquals(HASH, hash);
    }

    @Test
    void shouldReturnTheSameResult() {
        var hash1 = target.hash(VALUE);
        var hash2 = target.hash(VALUE);

        assertEquals(hash1, hash2);
    }
}