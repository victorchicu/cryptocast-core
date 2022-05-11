package com.coinbank.core.exceptions;

import com.coinbank.core.enums.ExchangeProvider;

public class UnsupportedExchangeProviderException extends RuntimeException {

    public UnsupportedExchangeProviderException(ExchangeProvider exchangeProvider) {
        super("Unsupported exchange provider: " + exchangeProvider);
    }
}
