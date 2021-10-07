package com.crypto.core.auth.repository.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "accounts")
public class AccountEntity extends BaseEntity {
    @Indexed(name = "email", unique = true, sparse = true)
    private String email;
    private String password;

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

    public static final class Field {
        public static final String EMAIL = "email";
    }
}
