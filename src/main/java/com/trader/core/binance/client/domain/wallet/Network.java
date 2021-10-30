package com.trader.core.binance.client.domain.wallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Network {
    private String coin;
    private String name;
    private String network;
    private String memoRegex;
    private String depositDesc;
    private String specialTips;
    private String addressRegex;
    private String withdrawDesc;
    private Integer minConfirm;
    private Integer unLockConfirm;
    private Boolean isDefault;
    private Boolean sameAddress;
    private Boolean depositEnable;
    private Boolean withdrawEnable;
    private Boolean resetAddressStatus;
    private BigDecimal withdrawFee;
    private BigDecimal withdrawIntegerMultiple;
    private BigDecimal withdrawMax;
    private BigDecimal withdrawMin;

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

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getMemoRegex() {
        return memoRegex;
    }

    public void setMemoRegex(String memoRegex) {
        this.memoRegex = memoRegex;
    }

    public String getDepositDesc() {
        return depositDesc;
    }

    public void setDepositDesc(String depositDesc) {
        this.depositDesc = depositDesc;
    }

    public String getSpecialTips() {
        return specialTips;
    }

    public void setSpecialTips(String specialTips) {
        this.specialTips = specialTips;
    }

    public String getAddressRegex() {
        return addressRegex;
    }

    public void setAddressRegex(String addressRegex) {
        this.addressRegex = addressRegex;
    }

    public String getWithdrawDesc() {
        return withdrawDesc;
    }

    public void setWithdrawDesc(String withdrawDesc) {
        this.withdrawDesc = withdrawDesc;
    }

    public Integer getMinConfirm() {
        return minConfirm;
    }

    public void setMinConfirm(Integer minConfirm) {
        this.minConfirm = minConfirm;
    }

    public Integer getUnLockConfirm() {
        return unLockConfirm;
    }

    public void setUnLockConfirm(Integer unLockConfirm) {
        this.unLockConfirm = unLockConfirm;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getSameAddress() {
        return sameAddress;
    }

    public void setSameAddress(Boolean sameAddress) {
        this.sameAddress = sameAddress;
    }

    public Boolean getDepositEnable() {
        return depositEnable;
    }

    public void setDepositEnable(Boolean depositEnable) {
        this.depositEnable = depositEnable;
    }

    public Boolean getWithdrawEnable() {
        return withdrawEnable;
    }

    public void setWithdrawEnable(Boolean withdrawEnable) {
        this.withdrawEnable = withdrawEnable;
    }

    public Boolean getResetAddressStatus() {
        return resetAddressStatus;
    }

    public void setResetAddressStatus(Boolean resetAddressStatus) {
        this.resetAddressStatus = resetAddressStatus;
    }

    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public BigDecimal getWithdrawIntegerMultiple() {
        return withdrawIntegerMultiple;
    }

    public void setWithdrawIntegerMultiple(BigDecimal withdrawIntegerMultiple) {
        this.withdrawIntegerMultiple = withdrawIntegerMultiple;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public BigDecimal getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(BigDecimal withdrawMin) {
        this.withdrawMin = withdrawMin;
    }
}
