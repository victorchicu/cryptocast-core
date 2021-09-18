package com.crypto.core.exceptions;

public class SymbolNotFoundException extends RuntimeException {
    private final String symbol;

    public SymbolNotFoundException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
