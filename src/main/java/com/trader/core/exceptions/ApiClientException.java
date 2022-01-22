package com.trader.core.exceptions;

public class ApiClientException extends RuntimeException {
    public ApiClientException(String message) {
        super(message);
    }
}
