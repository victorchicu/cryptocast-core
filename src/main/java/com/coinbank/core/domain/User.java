package com.coinbank.core.domain;

import com.coinbank.core.enums.ExchangeProvider;
import com.coinbank.core.enums.OAuth2Provider;

import java.util.Map;

public class User {
    private String id;
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private OAuth2Provider auth2Provider;
    private Map<String, CryptoExchange> cryptoExchanges;

    private User(Builder builder) {
        id = builder.id;
        email = builder.email;
        password = builder.password;
        imageUrl = builder.imageUrl;
        providerId = builder.providerId;
        auth2Provider = builder.auth2Provider;
        cryptoExchanges = builder.cryptoExchanges;
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

    public Map<String, CryptoExchange> getCryptoExchanges() {
        return cryptoExchanges;
    }

    public static class CryptoExchange {
        private String name;
        private String apiKey;
        private String secretKey;
        private ExchangeProvider provider;

        private CryptoExchange(Builder builder) {
            name = builder.name;
            apiKey = builder.apiKey;
            secretKey = builder.secretKey;
            provider = builder.provider;
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

        public ExchangeProvider getProvider() {
            return provider;
        }

        public void setProvider(ExchangeProvider provider) {
            this.provider = provider;
        }

        public static final class Builder {
            private String name;
            private String apiKey;
            private String secretKey;
            private ExchangeProvider provider;

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

            public Builder provider(ExchangeProvider provider) {
                this.provider = provider;
                return this;
            }

            public CryptoExchange build() {
                return new CryptoExchange(this);
            }
        }
    }

    public static final class Builder {
        private String id;
        private String email;
        private String password;
        private String imageUrl;
        private String providerId;
        private OAuth2Provider auth2Provider;
        private Map<String, CryptoExchange> cryptoExchanges;

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

        public Builder cryptoExchanges(Map<String, CryptoExchange> cryptoExchanges) {
            this.cryptoExchanges = cryptoExchanges;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
