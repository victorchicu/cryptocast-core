package com.crypto.core.binance.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "binance")
public class BinanceProperties {
    private String url;
    private String apiKey;
    private String secretKey;
    private Set<String> blacklist;
    private Map<String, Coin> tetherSymbol;
    private Map<String, Coin> symbolTether;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Set<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Set<String> blacklist) {
        this.blacklist = blacklist;
    }

    public Map<String, Coin> getTetherSymbol() {
        return tetherSymbol;
    }

    public void setTetherSymbol(Map<String, Coin> tetherSymbol) {
        this.tetherSymbol = tetherSymbol;
    }

    public Map<String, Coin> getSymbolTether() {
        return symbolTether;
    }

    public void setSymbolTether(Map<String, Coin> symbolTether) {
        this.symbolTether = symbolTether;
    }

    public static class Coin {
        private Integer icon;
        private String name;
        private String alias;

        public Integer getIcon() {
            return icon;
        }

        public void setIcon(Integer icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }
}
