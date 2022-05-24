package com.coinbank.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableFeignClients
@EnableMongoAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CoinbankCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoinbankCoreApplication.class, args);
    }
}
