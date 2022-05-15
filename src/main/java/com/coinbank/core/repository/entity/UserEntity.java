package com.coinbank.core.repository.entity;

import com.coinbank.core.enums.OAuth2Provider;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "users")
public class UserEntity extends BaseEntity {
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private OAuth2Provider auth2Provider;
    private Map<String, ApiKeyEntity> apiKeys;

    public UserEntity() {

    }

    private UserEntity(Builder builder) {
        setId(builder.id);
        setCreatedBy(builder.createdBy);
        setCreatedAt(builder.createdAt);
        setEmail(builder.email);
        setPassword(builder.password);
        setImageUrl(builder.imageUrl);
        setProviderId(builder.providerId);
        setAuth2Provider(builder.auth2Provider);
        setApiKeys(builder.apiKeys);
    }

    public static Builder newBuilder() {
        return new Builder();
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

    public OAuth2Provider getAuth2Provider() {
        return auth2Provider;
    }

    public void setAuth2Provider(OAuth2Provider auth2Provider) {
        this.auth2Provider = auth2Provider;
    }

    public Map<String, ApiKeyEntity> getApiKeys() {
        return apiKeys;
    }

    public void setApiKeys(Map<String, ApiKeyEntity> apiKeys) {
        this.apiKeys = apiKeys;
    }

    public static final class Builder {
        private String id;
        private String createdBy;
        private Instant createdAt;
        private String email;
        private String password;
        private String imageUrl;
        private String providerId;
        private OAuth2Provider auth2Provider;
        private Map<String, ApiKeyEntity> apiKeys;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
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

        public Builder apiKeys(Map<String, ApiKeyEntity> apiKeys) {
            this.apiKeys = apiKeys;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }

    public static final class Field {
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String IMAGE_URL = "imageUrl";
        public static final String PROVIDER = "provider";
        public static final String PROVIDER_ID = "providerId";
        public static final String API_KEYS = "apiKeys";
    }
}
