package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "bot",
        description = "Trading bot for Binance platform",
        subcommands = {TrackCommand.class}
)
public class BotCommand extends BaseCommand {
    @Override
    public void run() {
        //NOP
    }
}