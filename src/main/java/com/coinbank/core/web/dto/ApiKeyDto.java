package com.coinbank.core.web.dto;

import com.coinbank.core.enums.Exchange;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ApiKeyDto {
    private final String label;
    private final String apiKey;
    private final String secretKey;
    private final Exchange exchange;

    @JsonCreator
    public ApiKeyDto(String label, String apiKey, String secretKey, Exchange exchange) {
        this.label = label;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.exchange = exchange;
    }

    public String getLabel() {
        return label;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Exchange getExchange() {
        return exchange;
    }
}
