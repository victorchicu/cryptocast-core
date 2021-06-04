package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.BaseTest;
import com.cryptostrophe.bot.picocli.commands.specific.BotCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;

public class PicoCliServiceIT extends BaseTest {
    @Test
    public void should_execute_track_help_command() {
        int exitCode = picoCliService.execute("track", "help");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_execute_track_with_single_symbol_param() {
        int exitCode = picoCliService.execute("track", "BTCUSDT");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_execute_track_with_multiple_symbol_params() {
        int exitCode = picoCliService.execute("track", "BTCUSDT", "1000SHIBUSDT");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_execute_track_with_missing_params_then_expect_error() {
        int exitCode = picoCliService.execute("track");
        Assertions.assertNotEquals(0, exitCode);
    }
}
