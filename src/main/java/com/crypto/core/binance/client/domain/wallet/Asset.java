package com.crypto.core.binance.client.domain.wallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Asset {
    private String coin;
    private Boolean depositAllEnable;
    private String free;
    private String freeze;
    private String ipoable;
    private String ipoing;
    private Boolean isLegalMoney;
    private String locked;
    private String name;
    private List<Network> networkList;
    private String storage;
    private Boolean trading;
    private Boolean withdrawAllEnable;
    private String withdrawing;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Boolean getDepositAllEnable() {
        return depositAllEnable;
    }

    public void setDepositAllEnable(Boolean depositAllEnable) {
        this.depositAllEnable = depositAllEnable;
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

    public String getIpoing() {
        return ipoing;
    }

    public void setIpoing(String ipoing) {
        this.ipoing = ipoing;
    }

    public Boolean getLegalMoney() {
        return isLegalMoney;
    }

    public void setLegalMoney(Boolean legalMoney) {
        isLegalMoney = legalMoney;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Network> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<Network> networkList) {
        this.networkList = networkList;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public Boolean getTrading() {
        return trading;
    }

    public void setTrading(Boolean trading) {
        this.trading = trading;
    }

    public Boolean getWithdrawAllEnable() {
        return withdrawAllEnable;
    }

    public void setWithdrawAllEnable(Boolean withdrawAllEnable) {
        this.withdrawAllEnable = withdrawAllEnable;
    }

    public String getWithdrawing() {
        return withdrawing;
    }

    public void setWithdrawing(String withdrawing) {
        this.withdrawing = withdrawing;
    }
}
