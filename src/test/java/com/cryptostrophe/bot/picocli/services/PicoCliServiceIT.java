package com.cryptostrophe.bot.picocli.services;

import com.cryptostrophe.bot.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class PicoCliServiceIT extends BaseTest {
    @Test
    public void should_parse_bot_stop_command() {
        CommandLine.ParseResult parseResult = picoCliService.parse("bot stop");
        Assertions.assertEquals(0, parseResult.errors().size());
    }

    @Test
    public void should_parse_bot_help_command() {
        CommandLine.ParseResult parseResult = picoCliService.parse("bot help");
        Assertions.assertEquals(0, parseResult.errors().size());
    }

    @Test
    public void should_parse_track_help_command() {
        CommandLine.ParseResult parseResult = picoCliService.parse("track help");
        Assertions.assertEquals(0, parseResult.errors().size());
    }

    @Test
    public void should_parse_track_with_single_symbol_param() {
        CommandLine.ParseResult parseResult = picoCliService.parse("track BTCUSDT");
        Assertions.assertEquals(0, parseResult.errors().size());
    }

    @Test
    public void should_parse_track_with_multiple_symbol_params() {
        CommandLine.ParseResult parseResult = picoCliService.parse("track BTCUSDT 1000SHIBUSDT");
        Assertions.assertEquals(0, parseResult.errors().size());
    }

    @Test
    public void should_expect_errors_if_missing_symbol_parameters() {
        CommandLine.ParseResult parseResult = picoCliService.parse("track");
        Assertions.assertFalse(parseResult.errors().isEmpty());
    }
}
