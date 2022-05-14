package com.coinbank.core.domain;

import com.coinbank.core.enums.OAuth2Provider;

import java.util.Map;

public class User {
    private String id;
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private OAuth2Provider auth2Provider;
    private Map<String, ApiKey> apiKeys;

    private User(Builder builder) {
        id = builder.id;
        email = builder.email;
        password = builder.password;
        imageUrl = builder.imageUrl;
        providerId = builder.providerId;
        auth2Provider = builder.auth2Provider;
        apiKeys = builder.apiKeys;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProviderId() {
        return providerId;
    }

    public OAuth2Provider getAuth2Provider() {
        return auth2Provider;
    }

    public Map<String, ApiKey> getApiKeys() {
        return apiKeys;
    }

    public void addApiKey(ApiKey apiKey) {
        apiKeys.put(apiKey.getLabel(), apiKey);
    }

    public void deleteApiKey(String label) {
        apiKeys.remove(label);
    }

    public static final class Builder {
        private String id;
        private String email;
        private String password;
        private String imageUrl;
        private String providerId;
        private OAuth2Provider auth2Provider;
        private Map<String, ApiKey> apiKeys;

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

        public Builder auth2Provider(OAuth2Provider auth2Provider) {
            this.auth2Provider = auth2Provider;
            return this;
        }

        public Builder exchanges(Map<String, ApiKey> exchanges) {
            this.apiKeys = exchanges;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
