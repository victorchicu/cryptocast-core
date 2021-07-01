package com.crypto.bot.picocli.commands;

import com.crypto.bot.BaseTest;
import com.crypto.bot.picocli.commands.specific.TrackSymbolCommand;
import com.google.gson.Gson;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class TrackSymbolCommandIT extends BaseTest {
    @Autowired
    Gson gson;

    TrackSymbolCommand trackSymbolCommand;

    @BeforeEach
    public void setUp() {
        trackSymbolCommand = new TrackSymbolCommand(
                binanceService,
                binanceProperties,
                telegramBotService,
                freeMarkerTemplateService,
                subscriptionsService
        );
    }

    @Test
    public void should_not_track_if_empty_symbol_list() throws Exception {
        String jsonString = "{\n" +
                "    \"message\": {\n" +
                "        \"from\": {\n" +
                "            \"id\": 123456789\n" +
                "        }\n" +
                "    }\n" +
                "}";
        Update update = gson.fromJson(jsonString, Update.class);
        trackSymbolCommand.subscribeToSymbolMiniTickerEvents(update, Collections.emptyList());
    }
}
