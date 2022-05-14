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
    private Map<String, AssetConfig> assets;

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

    public Map<String, AssetConfig> getAssets() {
        return assets;
    }

    public void setAssets(Map<String, AssetConfig> assets) {
        this.assets = assets;
    }

    public static class AssetConfig {
        private String symbol;
        private String fullName;
        private Integer iconIndex;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Integer getIconIndex() {
            return iconIndex;
        }

        public void setIconIndex(Integer iconIndex) {
            this.iconIndex = iconIndex;
        }
    }
}
