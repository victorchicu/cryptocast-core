package com.coinbank.core.domain.exceptions;

public class IllegalAssetException extends RuntimeException {
    public IllegalAssetException(String assetName) {
        super("Illegal asset " + assetName);
    }
}
