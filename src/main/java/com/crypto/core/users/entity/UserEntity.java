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

    public static final class Field {
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String IMAGE_URL = "image_url";
        public static final String PROVIDER = "provider";
        public static final String PROVIDER_ID = "provider_id";
    }
}
