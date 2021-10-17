package com.crypto.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableFeignClients
@EnableMongoAuditing
@SpringBootApplication
public class CryptoCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoCoreApplication.class, args);
    }
}
