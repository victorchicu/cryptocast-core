package com.crypto.core.binance.client.domain.wallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {
    private String coin;
    private String name;
    private Boolean trading;
    private Boolean flagged;
    private Boolean isLegalMoney;
    private Boolean depositAllEnable;
    private Boolean withdrawAllEnable;
    private BigDecimal free;
    private BigDecimal freeze;
    private BigDecimal ipoing;
    private BigDecimal locked;
    private BigDecimal ipoable;
    private BigDecimal storage;
    private BigDecimal withdrawing;
    private List<Network> networkList;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTrading() {
        return trading;
    }

    public void setTrading(Boolean trading) {
        this.trading = trading;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public Boolean getLegalMoney() {
        return isLegalMoney;
    }

    public void setLegalMoney(Boolean legalMoney) {
        isLegalMoney = legalMoney;
    }

    public Boolean getDepositAllEnable() {
        return depositAllEnable;
    }

    public void setDepositAllEnable(Boolean depositAllEnable) {
        this.depositAllEnable = depositAllEnable;
    }

    public Boolean getWithdrawAllEnable() {
        return withdrawAllEnable;
    }

    public void setWithdrawAllEnable(Boolean withdrawAllEnable) {
        this.withdrawAllEnable = withdrawAllEnable;
    }

    public BigDecimal getFree() {
        return free;
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public BigDecimal getIpoing() {
        return ipoing;
    }

    public void setIpoing(BigDecimal ipoing) {
        this.ipoing = ipoing;
    }

    public BigDecimal getLocked() {
        return locked;
    }

    public void setLocked(BigDecimal locked) {
        this.locked = locked;
    }

    public BigDecimal getIpoable() {
        return ipoable;
    }

    public void setIpoable(BigDecimal ipoable) {
        this.ipoable = ipoable;
    }

    public BigDecimal getStorage() {
        return storage;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    public BigDecimal getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(BigDecimal withdrawing) {
        this.withdrawing = withdrawing;
    }

    public List<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<Network> networkList) {
        this.networkList = networkList;
    }
}
