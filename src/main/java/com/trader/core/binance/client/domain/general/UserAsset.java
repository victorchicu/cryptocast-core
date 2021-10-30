package com.trader.core.binance.client.domain.general;

/**
 * An user asset Binance supports.
 */
public class UserAsset {
    private String asset;
    private String btcValuation;
    private String free;
    private String freeze;
    private String ipoable;
    private String locked;
    private String plateType;
    private Integer test;
    private String withdrawing;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getBtcValuation() {
        return btcValuation;
    }

    public void setBtcValuation(String btcValuation) {
        this.btcValuation = btcValuation;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public String getIpoable() {
        return ipoable;
    }

    public void setIpoable(String ipoable) {
        this.ipoable = ipoable;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public String getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(String withdrawing) {
        this.withdrawing = withdrawing;
    }
}
