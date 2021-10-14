package com.crypto.core.users.entity;

import com.crypto.core.auth.enums.AuthProvider;
import com.crypto.core.users.BaseEntity;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity extends BaseEntity {
    @Indexed(name = "email", unique = true, sparse = true)
    private String email;
    private String password;
    private String imageUrl;
    private String providerId;
    private String apiKey;
    private String secretKey;
    private AuthProvider provider;

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

    public static final class Field {
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String IMAGE_URL = "imageUrl";
        public static final String PROVIDER = "provider";
        public static final String PROVIDER_ID = "providerId";
        public static final String API_KEY = "apiKey";
        public static final String SECRET_KEY = "secretKey";
    }
}
