package com.coinbank.core.domain;

import com.coinbank.core.enums.OAuth2Provider;

import java.util.Map;

public class User {
    private String id;
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private String displayName;
    private OAuth2Provider auth2Provider;
    private Map<String, ApiKey> exchanges;

    private User(Builder builder) {
        setId(builder.id);
        setEmail(builder.email);
        setPassword(builder.password);
        setImageUrl(builder.imageUrl);
        setProviderId(builder.providerId);
        setDisplayName(builder.displayName);
        setAuth2Provider(builder.auth2Provider);
        setExchanges(builder.exchanges);
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public OAuth2Provider getAuth2Provider() {
        return auth2Provider;
    }

    public void setAuth2Provider(OAuth2Provider auth2Provider) {
        this.auth2Provider = auth2Provider;
    }

    public Map<String, ApiKey> getExchanges() {
        return exchanges;
    }

    public void setExchanges(Map<String, ApiKey> exchanges) {
        this.exchanges = exchanges;
    }

    public void addApiKey(ApiKey apiKey) {
        exchanges.put(apiKey.getLabel(), apiKey);
    }

    public void deleteApiKeyByLabel(String label) {
        exchanges.remove(label);
    }

    public static final class Builder {
        private String id;
        private String email;
        private String password;
        private String imageUrl;
        private String providerId;
        private String displayName;
        private OAuth2Provider auth2Provider;
        private Map<String, ApiKey> exchanges;

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

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder auth2Provider(OAuth2Provider auth2Provider) {
            this.auth2Provider = auth2Provider;
            return this;
        }

        public Builder apiKeys(Map<String, ApiKey> apiKeys) {
            this.exchanges = apiKeys;
            return this;
        }

        public Builder exchanges(Map<String, ApiKey> exchanges) {
            this.exchanges = exchanges;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
