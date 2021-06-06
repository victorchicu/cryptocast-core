package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@CommandLine.Command(
        name = "bot",
        description = "Trading bot for Binance platform",
        subcommands = {
                GetSymbolCommand.class, StopCommand.class, TrackSymbolCommand.class
        }
)
public class BotCommand extends BaseCommand {
    private static final Logger LOG = LoggerFactory.getLogger(BotCommand.class);

    private final Update update;

    public BotCommand(Update update) {
        this.update = update;
    }

    @Override
    public void run() {
        String helpString = usage(this);
        LOG.info(helpString);
        //TODO: Send telegram message
    }

    public Update getUpdate() {
        return update;
    }
}
