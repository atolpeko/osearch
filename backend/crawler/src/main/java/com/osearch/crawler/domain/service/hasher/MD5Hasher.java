package com.osearch.crawler.domain.service.hasher;

import java.security.MessageDigest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MD5Hasher implements Hasher {

    @Override
    public String hash(String string) {
        try {
            var md = MessageDigest.getInstance("MD5");
            var hashBytes = md.digest(string.getBytes());

            var sb = new StringBuilder();
            for (var b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            log.error("No MD5 hash algorithm found");
            return String.valueOf(string.hashCode());
        }
    }
}
