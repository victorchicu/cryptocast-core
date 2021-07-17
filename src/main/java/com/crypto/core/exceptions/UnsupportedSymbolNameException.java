package com.crypto.core.exceptions;

public class UnsupportedSymbolNameException extends RuntimeException {
    private final String symbol;

    public UnsupportedSymbolNameException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
