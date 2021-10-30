package com.trader.core.binance.assets.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetBalanceDto {
    private final String coin;
    private final String name;
    private final Integer icon;
    private final Boolean flagged;
    private final BigDecimal balance;
    private final BigDecimal usdtValue;

    @JsonCreator
    public AssetBalanceDto(
            String coin,
            String name,
            Integer icon,
            Boolean flagged,
            BigDecimal balance,
            BigDecimal usdtValue
    ) {
        this.coin = coin;
        this.name = name;
        this.icon = icon;
        this.flagged = flagged;
        this.balance = balance;
        this.usdtValue = usdtValue;
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

    public BigDecimal getUsdtValue() {
        return usdtValue;
    }

    @Override
    public String toString() {
        return "AssetBalanceDto{" +
                "coin='" + coin + '\'' +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", flagged=" + flagged +
                ", balance=" + balance +
                ", usdtValue=" + usdtValue +
                '}';
    }
}
