package com.crypto.core.auth.exceptions;

public class UnauthorizedRedirectException extends RuntimeException {
    public UnauthorizedRedirectException(String message) {
        super(message);
    }
}
