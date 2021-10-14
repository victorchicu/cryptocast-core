package com.crypto.core.binance.client.domain.account.request;

import com.crypto.core.binance.client.domain.account.NewOCOResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderListResponse extends NewOCOResponse {

}
