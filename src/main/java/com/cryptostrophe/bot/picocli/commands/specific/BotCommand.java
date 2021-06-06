package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "bot",
        description = "Trading bot for Binance platform",
        subcommands = {
                GetSymbolCommand.class, StopCommand.class, TrackSymbolCommand.class
        }
)
public class BotCommand extends BaseCommand {
    private static final Logger LOG = LoggerFactory.getLogger(BotCommand.class);

    @Override
    public void run() {
        String helpString = usage(this);
        LOG.info(helpString);
        //TODO: Send telegram message
    }
}
