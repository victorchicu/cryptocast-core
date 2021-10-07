package com.crypto.core.users.domain;

import com.crypto.core.auth.enums.AuthProvider;

public class User {
    private String id;
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private AuthProvider provider;

    private User(Builder builder) {
        id = builder.id;
        email = builder.email;
        password = builder.password;
        imageUrl = builder.imageUrl;
        providerId = builder.providerId;
        provider = builder.provider;
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

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public static final class Builder {
        private String id;
        private String email;
        private String password;
        private String imageUrl;
        private String providerId;
        private AuthProvider provider;

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

        public Builder provider(AuthProvider provider) {
            this.provider = provider;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
