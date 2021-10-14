package com.crypto.core.binance.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum SwapRemoveType {
    SINGLE, COMBINATION
}
