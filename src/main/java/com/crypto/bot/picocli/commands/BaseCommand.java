package com.crypto.bot.picocli.commands;

import com.crypto.bot.picocli.exceptions.UsageHelpException;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class BaseCommand implements Runnable {
    @CommandLine.Option(names = {"help"}, help = true)
    protected boolean usageHelpRequested;

    public static String usage(Object command) {
        try (OutputStream outputStream = new ByteArrayOutputStream()) {
            try (PrintStream printStream = new PrintStream(outputStream)) {
                CommandLine.usage(command, printStream);
                return outputStream.toString();
            }
        } catch (IOException e) {
            throw new UsageHelpException(e.getMessage(), e);
        }
    }
}
