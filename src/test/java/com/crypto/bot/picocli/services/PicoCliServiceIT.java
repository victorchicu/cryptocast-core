package com.crypto.bot.picocli.services;

import com.crypto.bot.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;

public class PicoCliServiceIT extends BaseTest {
    @Test
    public void should_parse_bot_commands() {
        List<String> commands = Arrays.asList(
                "bot stop",
                "bot help",
                "bot track help",
                "bot track BTCUSDT",
                "bot track BTCUSDT 1000SHIBUSDT"
        );
        commands.forEach(command -> {
            CommandLine.ParseResult parseResult = picoCliService.parse(command);
            Assertions.assertEquals(0, parseResult.errors().size());
        });
    }

    @Test
    public void should_expect_errors_if_track_command_missing_symbol_parameters() {
        CommandLine.ParseResult parseResult = picoCliService.parse("bot track");
        Assertions.assertFalse(parseResult.errors().isEmpty());
    }
}
