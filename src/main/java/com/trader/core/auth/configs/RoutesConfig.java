package com.trader.core.auth.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("routes")
public class RoutesConfig {
    private List<String> publicRoutes;

    public List<String> getPublicRoutes() {
        return publicRoutes;
    }

    public void setPublicRoutes(List<String> publicRoutes) {
        this.publicRoutes = publicRoutes;
    }
}
