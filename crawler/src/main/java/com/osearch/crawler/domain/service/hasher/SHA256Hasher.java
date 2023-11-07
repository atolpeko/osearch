package com.osearch.crawler.domain.service.hasher;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.log4j.Log4j2;

/**
 * SHA256Hasher is a class that provides functionality
 * to hash a given string using the SHA-256 algorithm.
 */
@Log4j2
public class SHA256Hasher implements Hasher {
    private static final MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No SHA-256 support", e);
        }
    }

    @Override
    public String hash(String string) {
        var hashBytes = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        var builder = new StringBuilder();
        for (var b : hashBytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
