package com.cryptostrophe.bot.services;

import com.cryptostrophe.bot.BaseTest;
import com.cryptostrophe.bot.picocli.commands.specific.BotCommand;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PicoCliServiceIT extends BaseTest {
    BotCommand botCommand = new BotCommand(new Update(), telegramBotService);
    
    @Test
    public void should_parse_bot_stop_command() {
        int exitCode = picoCliService.execute("bot stop");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_parse_bot_help_command() {
        int exitCode = picoCliService.execute("bot help");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_parse_track_help_command() {
        int exitCode = picoCliService.execute("track help");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_parse_track_with_single_symbol_param() {
        int exitCode = picoCliService.execute("track BTCUSDT");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_parse_track_with_multiple_symbol_params() {
        int exitCode = picoCliService.execute("track BTCUSDT 1000SHIBUSDT");
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    public void should_expect_errors_if_missing_symbol_parameters() {
        int exitCode = picoCliService.execute("track");
        Assertions.assertNotEquals(0, exitCode);
    }
}
