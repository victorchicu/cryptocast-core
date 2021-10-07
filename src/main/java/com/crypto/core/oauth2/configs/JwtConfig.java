package com.crypto.core.oauth2.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private Long tokenExpiresInMillis;
    private String tokenSecret;

    public Long getTokenExpiresInMillis() {
        return tokenExpiresInMillis;
    }

    public void setTokenExpiresInMillis(Long tokenExpiresInMillis) {
        this.tokenExpiresInMillis = tokenExpiresInMillis;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
