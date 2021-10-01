package com.crypto.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CryptoCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptoCoreApplication.class, args);
    }
}
