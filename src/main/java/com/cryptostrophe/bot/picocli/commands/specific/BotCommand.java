package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.picocli.services.PicoCliService;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.Optional;

@Component
@CommandLine.Command(
        name = "bot",
        description = "Trading bot for Binance platform",
        subcommands = {TrackSymbolCommand.class}
)
public class BotCommand extends BaseCommand {
    private static final Logger LOG = LoggerFactory.getLogger(BotCommand.class);

    private final PicoCliService picoCliService;
    private final TelegramBotService telegramBotService;

    public BotCommand(PicoCliService picoCliService, TelegramBotService telegramBotService) {
        this.picoCliService = picoCliService;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void run() {
        telegramBotService.setUpdateListener((List<Update> list) -> {
            list.forEach((Update update) -> {
                try {
                    handleUpdate(update);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> LOG.error(e.getMessage(), e));
    }

    public void handleUpdate(Update update) {
        CommandLine.ParseResult parseResult = picoCliService.parse(update.message().text());
        if (parseResult.errors().isEmpty()) {
            picoCliService.execute(update.message().text().split(" "));
        }
    }
}
