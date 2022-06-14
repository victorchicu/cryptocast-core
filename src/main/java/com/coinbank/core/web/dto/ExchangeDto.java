package com.coinbank.core.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ExchangeDto {
    private final String label;

    @JsonCreator
    public ExchangeDto(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
