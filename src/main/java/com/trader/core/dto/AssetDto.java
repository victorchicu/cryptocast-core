package com.trader.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.enums.Quotation;

import java.math.BigDecimal;

public class AssetDto {
    private final String name;
    private final String fullName;
    private final Integer iconIndex;
    private final BigDecimal free;
    private final BigDecimal frozen;
    private final BigDecimal price;
    private final BigDecimal priceChange;
    private final BigDecimal balance;
    private final Quotation quotation;

    @JsonCreator
    public AssetDto(
            String name,
            String fullName,
            Integer iconIndex,
            BigDecimal free,
            BigDecimal frozen,
            BigDecimal price,
            BigDecimal priceChange,
            BigDecimal balance,
            Quotation quotation
    ) {
        this.name = name;
        this.fullName = fullName;
        this.iconIndex = iconIndex;
        this.free = free;
        this.frozen = frozen;
        this.price = price;
        this.priceChange = priceChange;
        this.balance = balance;
        this.quotation = quotation;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getIconIndex() {
        return iconIndex;
    }

    public BigDecimal getFree() {
        return free;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Quotation getQuotation() {
        return quotation;
    }
}
