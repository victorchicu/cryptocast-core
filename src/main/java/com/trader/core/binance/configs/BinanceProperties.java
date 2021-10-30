package com.trader.core.binance.configs;

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
    private Boolean useTestnet;
    private Boolean useTestnetStreaming;
    private Set<String> blacklist;
    private Map<String, Asset> assets;

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

    public Map<String, Asset> getAssets() {
        return assets;
    }

    public void setAssets(Map<String, Asset> assets) {
        this.assets = assets;
    }

    public static class Asset {
        private String coin;
        private String name;
        private Integer icon;

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIcon() {
            return icon;
        }

        public void setIcon(Integer icon) {
            this.icon = icon;
        }
    }
}
