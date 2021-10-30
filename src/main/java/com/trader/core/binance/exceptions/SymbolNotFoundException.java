package com.trader.core.binance.exceptions;

public class SymbolNotFoundException extends RuntimeException {
    public SymbolNotFoundException(String symbolName) {
        super("Symbol not found: " + symbolName);
    }
}
