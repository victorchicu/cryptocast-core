package com.crypto.cli.picocli.exceptions;

import java.io.IOException;

public class UsageHelpException extends RuntimeException {
    public UsageHelpException(String message, IOException exception) {
        super(message, exception);
    }
}
