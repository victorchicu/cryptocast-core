package com.crypto.core.wallet.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetDto {
    private final String coin;
    private final String name;
    private final Integer icon;
    private final BigDecimal balance;

    @JsonCreator
    public AssetDto(String coin, String name, Integer icon, BigDecimal balance) {
        this.coin = coin;
        this.name = name;
        this.icon = icon;
        this.balance = balance;
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

    public BigDecimal getBalance() {
        return balance;
    }
}
