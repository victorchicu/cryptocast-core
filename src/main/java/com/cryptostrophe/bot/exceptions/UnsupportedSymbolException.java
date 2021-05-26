package com.cryptostrophe.bot.exceptions;

public class UnsupportedSymbolException extends RuntimeException {
    private final String symbol;

    public UnsupportedSymbolException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
