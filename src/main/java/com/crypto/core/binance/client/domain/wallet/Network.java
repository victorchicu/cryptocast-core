package com.crypto.core.binance.client.domain.wallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {
    private String addressRegex;
    private String coin;
    private String depositDesc;
    private Boolean depositEnable;
    private Boolean isDefault;
    private String memoRegex;
    private Integer minConfirm;
    private String name;
    private String network;
    private Boolean resetAddressStatus;
    private String specialTips;
    private Integer unLockConfirm;
    private String withdrawDesc;
    private Boolean withdrawEnable;
    private String withdrawFee;
    private String withdrawIntegerMultiple;
    private String withdrawMax;
    private String withdrawMin;
    private Boolean sameAddress;

    public String getAddressRegex() {
        return addressRegex;
    }

    public void setAddressRegex(String addressRegex) {
        this.addressRegex = addressRegex;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getDepositDesc() {
        return depositDesc;
    }

    public void setDepositDesc(String depositDesc) {
        this.depositDesc = depositDesc;
    }

    public Boolean getDepositEnable() {
        return depositEnable;
    }

    public void setDepositEnable(Boolean depositEnable) {
        this.depositEnable = depositEnable;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getMemoRegex() {
        return memoRegex;
    }

    public void setMemoRegex(String memoRegex) {
        this.memoRegex = memoRegex;
    }

    public Integer getMinConfirm() {
        return minConfirm;
    }

    public void setMinConfirm(Integer minConfirm) {
        this.minConfirm = minConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Boolean getResetAddressStatus() {
        return resetAddressStatus;
    }

    public void setResetAddressStatus(Boolean resetAddressStatus) {
        this.resetAddressStatus = resetAddressStatus;
    }

    public String getSpecialTips() {
        return specialTips;
    }

    public void setSpecialTips(String specialTips) {
        this.specialTips = specialTips;
    }

    public Integer getUnLockConfirm() {
        return unLockConfirm;
    }

    public void setUnLockConfirm(Integer unLockConfirm) {
        this.unLockConfirm = unLockConfirm;
    }

    public String getWithdrawDesc() {
        return withdrawDesc;
    }

    public void setWithdrawDesc(String withdrawDesc) {
        this.withdrawDesc = withdrawDesc;
    }

    public Boolean getWithdrawEnable() {
        return withdrawEnable;
    }

    public void setWithdrawEnable(Boolean withdrawEnable) {
        this.withdrawEnable = withdrawEnable;
    }

    public String getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(String withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public String getWithdrawIntegerMultiple() {
        return withdrawIntegerMultiple;
    }

    public void setWithdrawIntegerMultiple(String withdrawIntegerMultiple) {
        this.withdrawIntegerMultiple = withdrawIntegerMultiple;
    }

    public String getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(String withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public String getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(String withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public Boolean getSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(Boolean sameAddress) {
        this.sameAddress = sameAddress;
    }
}
