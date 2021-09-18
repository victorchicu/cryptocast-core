package com.crypto.core.picocli.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class TrackSymbolDto {
    private final String symbolName;

    @JsonCreator
    public TrackSymbolDto(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getSymbolName() {
        return symbolName;
    }
}
