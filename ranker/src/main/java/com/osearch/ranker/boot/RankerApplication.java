package com.osearch.ranker.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.osearch.ranker")
public class RankerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankerApplication.class, args);
    }
}
