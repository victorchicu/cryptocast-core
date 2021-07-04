package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
import picocli.CommandLine;

@CommandLine.Command(
        name = "bot",
        description = "Trading bot for Binance platform",
        subcommands = {
                StopCommand.class,
                GetSymbolCommand.class,
                TrackSymbolCommand.class
        }
)
public class BotCommand extends BaseCommand {
    private final Update update;
    private final TelegramBotService telegramBotService;

    public BotCommand(Update update, TelegramBotService telegramBotService) {
        this.update = update;
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void run() {
        if (usageHelpRequested) {
            String usageHelp = usage(this);
            telegramBotService.sendMessage(update.message().chat().id(), usageHelp);
        }
    }

    public Update getUpdate() {
        return update;
    }
}
