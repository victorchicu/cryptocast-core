package ai.cryptocast.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CryptocastCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(CryptocastCoreApplication.class, args);
    }
}
