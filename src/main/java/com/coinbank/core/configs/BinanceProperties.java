package com.coinbank.core.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Set;

@ConfigurationProperties(prefix = "binance")
public class BinanceProperties {
    private String url;
    private String apiKey;
    private String secretKey;
    private Boolean useTestnet;
    private Boolean useTestnetStreaming;
    private Set<String> blacklist;

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

    public Boolean getUseTestnet() {
        return useTestnet;
    }

    public void setUseTestnet(Boolean useTestnet) {
        this.useTestnet = useTestnet;
    }

    public Boolean getUseTestnetStreaming() {
        return useTestnetStreaming;
    }

    public void setUseTestnetStreaming(Boolean useTestnetStreaming) {
        this.useTestnetStreaming = useTestnetStreaming;
    }

    public Set<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Set<String> blacklist) {
        this.blacklist = blacklist;
    }
}
