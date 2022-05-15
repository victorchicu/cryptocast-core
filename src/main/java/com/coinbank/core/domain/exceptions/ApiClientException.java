package com.coinbank.core.domain.exceptions;

public class ApiClientException extends RuntimeException {
    public ApiClientException(String message) {
        super(message);
    }
}
