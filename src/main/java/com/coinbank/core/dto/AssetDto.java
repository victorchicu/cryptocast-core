package com.coinbank.core.dto;

import com.coinbank.core.enums.Exchange;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetDto {
    private final String name;
    private final String fullName;
    private final String apiKeyName;
    private final Exchange exchange;
    private final BigDecimal totalFunds;
    private final BigDecimal fundsAvailable;
    private final BigDecimal usedInAnyOutstandingOrders;

    @JsonCreator
    public AssetDto(String name, String fullName, String apiKeyName, Exchange exchange, BigDecimal totalFunds, BigDecimal fundsAvailable, BigDecimal usedInAnyOutstandingOrders) {
        this.name = name;
        this.fullName = fullName;
        this.apiKeyName = apiKeyName;
        this.exchange = exchange;
        this.totalFunds = totalFunds;
        this.fundsAvailable = fundsAvailable;
        this.usedInAnyOutstandingOrders = usedInAnyOutstandingOrders;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getApiKeyName() {
        return apiKeyName;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public BigDecimal getTotalFunds() {
        return totalFunds;
    }

    public BigDecimal getFundsAvailable() {
        return fundsAvailable;
    }

    public BigDecimal getUsedInAnyOutstandingOrders() {
        return usedInAnyOutstandingOrders;
    }
}
