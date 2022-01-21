package com.trader.core.users.domain;

import com.trader.core.enums.ExchangeProvider;
import com.trader.core.enums.OAuth2Provider;

public class User {
    private String id;
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private String apiKey;
    private String secretKey;
    private OAuth2Provider provider;
    private ExchangeProvider exchange;

    private User(Builder builder) {
        id = builder.id;
        email = builder.email;
        password = builder.password;
        imageUrl = builder.imageUrl;
        providerId = builder.providerId;
        apiKey = builder.apiKey;
        secretKey = builder.secretKey;
        provider = builder.provider;
        exchange = builder.exchange;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
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

    public OAuth2Provider getProvider() {
        return provider;
    }

    public void setProvider(OAuth2Provider provider) {
        this.provider = provider;
    }

    public ExchangeProvider getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeProvider exchange) {
        this.exchange = exchange;
    }

    public static final class Builder {
        private String id;
        private String email;
        private String password;
        private String imageUrl;
        private String providerId;
        private String apiKey;
        private String secretKey;
        private OAuth2Provider provider;
        private ExchangeProvider exchange;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder providerId(String providerId) {
            this.providerId = providerId;
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

        public Builder provider(OAuth2Provider provider) {
            this.provider = provider;
            return this;
        }

        public Builder exchange(ExchangeProvider exchange) {
            this.exchange = exchange;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
