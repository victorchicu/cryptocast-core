package com.cryptostrophe.bot.picocli.exceptions;

import java.io.IOException;

public class PrintCommandHelpException extends RuntimeException {
    public PrintCommandHelpException(String message, IOException exception) {
        super(message, exception);
    }
}
