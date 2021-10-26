package com.crypto.core.binance.client.domain.account;

import com.crypto.core.binance.client.constant.BinanceApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * An asset balance in an Account.
 *
 * @see Account
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetBalance {
    /*
      Asset full name
     */
    private String name;

    /**
     * Asset symbol.
     */
    private String asset;


    /*
      Icon index. Specific to Binance
     */
    private Integer icon;


    /**
     * Added to watchlist
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("asset", asset)
                .append("name", name)
                .append("free", free)
                .append("locked", locked)
                .append("flagged", flagged)
                .append("icon", icon)
                .toString();
    }
}
