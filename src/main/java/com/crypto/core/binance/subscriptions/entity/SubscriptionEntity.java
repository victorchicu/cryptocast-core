package com.crypto.core.binance.subscriptions.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = SubscriptionEntity.COLLECTION_NAME)
public class SubscriptionEntity extends BaseEntity {
    public static final String COLLECTION_NAME = "subscriptions";

    private String symbolName;

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
}
