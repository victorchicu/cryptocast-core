package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "bot",
        subcommands = {
                TrackCommand.class
        }
)
public class BotCommand extends BaseCommand {
    @Override
    public void run() {
        System.out.println("Bot command");
    }
}