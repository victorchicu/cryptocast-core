package com.trader.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trader.core.enums.Quotation;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FundsBalance {
    /**
     * Asset symbol.
     */
    private String asset;

    /**
     * In watchlist
     */
    private Boolean flagged;

    /**
     * Available balance.
     */
    private BigDecimal free;

    /**
     * Locked by open orders.
     */
    private BigDecimal locked;

    /**
     * Asset market price
     */
    private BigDecimal price;

    /**
     * Asset balance price
     */
    private BigDecimal balance;

    /**
     * Asset quotation
     */
    private Quotation quotation;


    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
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

    @Override
    public String toString() {
        return "CoinBalance{" +
                "asset='" + asset + '\'' +
                ", flagged=" + flagged +
                ", free=" + free +
                ", locked=" + locked +
                ", price=" + price +
                ", balance=" + balance +
                ", quotation=" + quotation +
                '}';
    }
}

