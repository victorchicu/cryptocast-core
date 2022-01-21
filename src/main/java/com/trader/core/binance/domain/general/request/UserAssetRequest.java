package com.trader.core.binance.domain.general.request;

public class UserAssetRequest {
    private boolean needBtcValuation;

    public boolean isNeedBtcValuation() {
        return needBtcValuation;
    }

    public UserAssetRequest setNeedBtcValuation(boolean needBtcValuation) {
        this.needBtcValuation = needBtcValuation;
        return this;
    }
}
