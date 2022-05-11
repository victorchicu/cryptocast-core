package com.coinbank.core.exceptions;

public class NotFoundEmailException extends EmailException {
    public NotFoundEmailException(String emailAddress) {
        super(String.format("%s not found", emailAddress));
    }
}
