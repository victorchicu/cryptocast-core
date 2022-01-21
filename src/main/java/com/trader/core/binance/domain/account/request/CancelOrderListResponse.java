package com.trader.core.binance.domain.account.request;

import com.trader.core.binance.domain.account.NewOCOResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderListResponse extends NewOCOResponse {

}
