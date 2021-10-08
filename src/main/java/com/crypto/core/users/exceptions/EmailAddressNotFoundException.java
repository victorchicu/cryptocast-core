package com.crypto.core.users.exceptions;

public class EmailAddressNotFoundException extends RuntimeException {
    public EmailAddressNotFoundException(String message) {
        super(message);
    }
}
