package com.trader.core.binance.assets.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.trader.core.binance.assets.enums.Quotation;

import java.math.BigDecimal;

public class AssetBalanceDto {
    private final String asset;
    private final String fullName;
    private final Integer iconIndex;
    private final Boolean flagged;
    private final BigDecimal free;
    private final BigDecimal frozen;
    private final BigDecimal price;
    private final BigDecimal balance;
    private final Quotation quotation;

    @JsonCreator
    public AssetBalanceDto(String asset, String fullName, Integer iconIndex, Boolean flagged, BigDecimal free, BigDecimal frozen, BigDecimal price, BigDecimal balance, Quotation quotation) {
        this.asset = asset;
        this.fullName = fullName;
        this.iconIndex = iconIndex;
        this.flagged = flagged;
        this.free = free;
        this.frozen = frozen;
        this.price = price;
        this.balance = balance;
        this.quotation = quotation;
    }

    public String getAsset() {
        return asset;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getIconIndex() {
        return iconIndex;
    }

    public Boolean getFlagged() {
        return flagged;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public Quotation getQuotation() {
        return quotation;
    }
}
