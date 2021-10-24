package com.crypto.core.wallet.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetDto {
    private final String coin;
    private final String name;
    private final Integer icon;
    private final Boolean flagged;
    private final BigDecimal balance;
    private final BigDecimal openPrice;
    private final BigDecimal highPrice;
    private final BigDecimal lowPrice;
    private final BigDecimal averagePrice;

    @JsonCreator
    public AssetDto(
            String coin,
            String name,
            Integer icon,
            Boolean flagged,
            BigDecimal balance,
            BigDecimal openPrice,
            BigDecimal highPrice,
            BigDecimal lowPrice,
            BigDecimal averagePrice
    ) {
        this.coin = coin;
        this.name = name;
        this.icon = icon;
        this.flagged = flagged;
        this.balance = balance;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.averagePrice = averagePrice;
    }

    public String getCoin() {
        return coin;
    }

    public String getName() {
        return name;
    }

    public Integer getIcon() {
        return icon;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }
}
