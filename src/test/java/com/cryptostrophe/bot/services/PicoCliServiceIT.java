package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.BaseTest;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;

public class PicoCliServiceIT extends BaseTest {
    @Test
    public void should_print() {
        CommandLine commandLine = new CommandLine(new BotCommand());
        commandLine.setExecutionStrategy(new CommandLine.RunAll());
        int exitCode = commandLine.execute("track", "help");
        Object executionResult = commandLine.getExecutionResult();
        String usage = commandLine.getUsageMessage();
        System.out.println(usage);
    }

    @CommandLine.Command(
            name = "bot",
            subcommands = {
                    TrackCommand.class
            }
    )
    static class BotCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("Bot command");
        }
    }

    @CommandLine.Command(
            name = "track",
            description = "24hr rolling window mini-ticker statistics for all symbols that changed"
    )
    static class TrackCommand implements Runnable {
        @CommandLine.Option(names = {"help"}, help = true, description = "Display this help message.")
        private boolean usageHelpRequested;

        @CommandLine.Parameters(
                arity = "1..*",
                paramLabel = "<symbols>",
                description = "The trading 'symbol' or shortened name (typically in capital letters) that refer to a coin on a trading platform. For example: BTCUSDT"
        )
        private String[] symbols;

        @Override
        public void run() {
            if (usageHelpRequested) {
                System.out.println("Usage help requested");
            } else {
                System.out.println("Process track command");
            }
        }
    }

    public static String usage(Object self) {
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        CommandLine.usage(self, printStream);
        String helpString = outputStream.toString();
        System.out.println(helpString);
        return helpString;
    }
}
