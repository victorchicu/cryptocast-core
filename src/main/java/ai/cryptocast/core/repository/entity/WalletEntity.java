package ai.cryptocast.core.repository.entity;

import ai.cryptocast.core.enums.Exchange;

public class WalletEntity {
    private String name;
    private String apiKey;
    private String secretKey;
    private Exchange exchange;

    public WalletEntity() {
        //
    }

    private WalletEntity(Builder builder) {
        setName(builder.name);
        setApiKey(builder.apiKey);
        setSecretKey(builder.secretKey);
        setExchange(builder.exchange);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public static final class Builder {
        private String name;
        private String apiKey;
        private String secretKey;
        private Exchange exchange;

        private Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Builder exchange(Exchange exchange) {
            this.exchange = exchange;
            return this;
        }

        public WalletEntity build() {
            return new WalletEntity(this);
        }
    }
}
