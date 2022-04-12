package com.trader.core.domain;

import com.trader.core.enums.Quotation;

import java.math.BigDecimal;

public class Asset {
    private String asset;
    private Integer iconIndex;
    private BigDecimal free;
    private BigDecimal locked;
    private BigDecimal price;
    private BigDecimal priceChange;
    private BigDecimal balance;
    private Quotation quotation;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Integer getIconIndex() {
        return iconIndex;
    }

    public void setIconIndex(Integer iconIndex) {
        this.iconIndex = iconIndex;
    }

    public BigDecimal getFree() {
        return free;
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getLocked() {
        return locked;
    }

    public void setLocked(BigDecimal locked) {
        this.locked = locked;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
}

