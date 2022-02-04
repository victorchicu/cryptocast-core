package com.trader.core.exceptions;

public class FundsNotFoundException extends RuntimeException {
    public FundsNotFoundException(String fundsName) {
        super("Asset " + fundsName + " not supported");
    }
}
