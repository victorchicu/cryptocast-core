package com.coinbank.core.exceptions;

public class IllegalAssetException extends RuntimeException {
    public IllegalAssetException(String assetName) {
        super("Illegal asset " + assetName);
    }
}
