package com.coinbank.core.repository.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.Instant;

public abstract class BaseEntity {
    @Id
    private String id;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Instant createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static class Field {
        public static final String ID = "_id";
        public static final String CREATED_BY = "createdBy";
        public static final String CREATED_AT = "createdAt";
    }
}
