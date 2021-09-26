package com.crypto.core.exchanges.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SymbolDto {
    private final String name;

    @JsonCreator
    public SymbolDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
