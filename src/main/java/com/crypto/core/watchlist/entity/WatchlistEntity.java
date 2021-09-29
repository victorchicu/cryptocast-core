package com.crypto.core.watchlist.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = WatchlistEntity.COLLECTION_NAME)
public class WatchlistEntity extends BaseEntity {
    public static final String COLLECTION_NAME = "subscriptions";

    private String symbolName;

    private WatchlistEntity(Builder builder) {symbolName = builder.symbolName;}

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public static class Field {
        public static final String ID = "_id";
        public static final String CREATED_AT = "createdAt";
        public static final String CREATED_BY = "createdBy";
        public static final String SYMBOL_NAME = "symbolName";
        public static final String REQUESTER_ID = "requesterId";
    }

    public static final class Builder {
        private String symbolName;

        private Builder() {}

        public Builder symbolName(String symbolName) {
            this.symbolName = symbolName;
            return this;
        }

        public WatchlistEntity build() {
            return new WatchlistEntity(this);
        }
    }
}
