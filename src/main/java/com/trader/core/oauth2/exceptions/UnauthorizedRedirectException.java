package com.trader.core.oauth2.exceptions;

public class UnauthorizedRedirectException extends RuntimeException {
    public UnauthorizedRedirectException(String message) {
        super(message);
    }
}
