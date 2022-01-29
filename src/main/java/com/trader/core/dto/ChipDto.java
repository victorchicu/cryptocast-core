package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ChipDto {
    private final String name;

    @JsonCreator
    public ChipDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
