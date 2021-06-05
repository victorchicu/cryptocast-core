package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.services.TelegramBotService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "track",
        description = "24hr rolling window mini-ticker statistics for all symbols that changed"
)
public class TrackCommand extends BaseCommand {
    private final TelegramBotService telegramBotService;

    @CommandLine.Option(names = {"help"}, help = true, description = "Display this help message.")
    private boolean usageHelpRequested;
    @CommandLine.Parameters(arity = "1..*", paramLabel = "<symbols>", description = "The trading 'symbol' or shortened name (typically in capital letters) that refer to a coin on a trading platform. For example: BTCUSDT")
    private String[] symbols;

    public TrackCommand(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void run() {
        if (usageHelpRequested) {
            String usageHelp = usage(this);
            telegramBotService.sendMessage(null, usageHelp);
        } else {
            System.out.println("Process track command");
        }
    }
}