package com.coinbank.core.entity;

import com.coinbank.core.enums.ExchangeProvider;
import com.coinbank.core.enums.OAuth2Provider;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "users")
@CompoundIndexes({@CompoundIndex(
        name = "email_exchange_idx",
        def = "{'email' : 1, 'exchange' : 1}",
        unique = true
)})
public class UserEntity extends BaseEntity {
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private OAuth2Provider auth2Provider;
    private Map<String, CryptoExchange> cryptoExchanges;

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

    public Map<String, CryptoExchange> getCryptoExchanges() {
        return cryptoExchanges;
    }

    public void setCryptoExchanges(Map<String, CryptoExchange> cryptoExchanges) {
        this.cryptoExchanges = cryptoExchanges;
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

    public static final class Field {
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String IMAGE_URL = "imageUrl";
        public static final String PROVIDER = "provider";
        public static final String PROVIDER_ID = "providerId";
        public static final String API_KEY = "apiKey";
        public static final String SECRET_KEY = "secretKey";
        public static final String EXCHANGE_PROVIDER = "exchangeProvider";
    }
}
