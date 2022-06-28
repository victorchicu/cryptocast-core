package com.coinbank.core.web.dto;

import com.coinbank.core.enums.ExchangeType;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetBalanceDto {
    private final String name;
    private final String fullName;
    private final ExchangeType exchange;
    private final BigDecimal totalFunds;
    private final BigDecimal fundsAvailable;
    private final BigDecimal usedInAnyOutstandingOrders;

    @JsonCreator
    public AssetBalanceDto(String name, String fullName, ExchangeType exchange, BigDecimal totalFunds, BigDecimal fundsAvailable, BigDecimal usedInAnyOutstandingOrders) {
        this.name = name;
        this.fullName = fullName;
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

    public ExchangeType getExchange() {
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
