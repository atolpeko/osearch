package com.osearch.ranker.boot.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.osearch.ranker.adapter.out.*")
@EntityScan("com.osearch.ranker.adapter.out.entity")
@EnableTransactionManagement
public class JpaConfig {

}
