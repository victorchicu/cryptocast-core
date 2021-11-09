package com.trader.core.binance.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.trader.core.assets.enums.Quotation;
import com.trader.core.binance.client.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * An asset balance in an Account.
 *
 * @see Account
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetBalance {
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
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("asset", asset)
                .append("free", free)
                .append("locked", locked)
                .toString();
    }
}
