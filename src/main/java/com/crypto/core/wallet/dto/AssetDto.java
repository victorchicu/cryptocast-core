package com.crypto.core.wallet.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.math.BigDecimal;

public class AssetDto {
    private final String coin;
    private final String name;
    private final Integer icon;
    private final Boolean flagged;
    private final BigDecimal balance;

    @JsonCreator
    public AssetDto(String coin, String name, Integer icon, Boolean flagged, BigDecimal balance) {
        this.coin = coin;
        this.name = name;
        this.icon = icon;
        this.flagged = flagged;
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

    public Boolean getFlagged() {
        return flagged;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
