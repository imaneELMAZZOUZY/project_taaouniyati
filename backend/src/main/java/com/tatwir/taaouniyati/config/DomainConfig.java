package com.tatwir.taaouniyati.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.tatwir.taaouniyati.domain")
@EnableJpaRepositories("com.tatwir.taaouniyati.repos")
@EnableTransactionManagement
public class DomainConfig {
}
