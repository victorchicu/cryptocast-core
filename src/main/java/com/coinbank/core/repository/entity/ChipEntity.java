package com.coinbank.core.repository.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chips")
public class ChipEntity extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
