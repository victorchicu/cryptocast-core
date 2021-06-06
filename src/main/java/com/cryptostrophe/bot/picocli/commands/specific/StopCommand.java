package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "stop",
        description = "Stop Bot"
)
public class StopCommand extends BaseCommand {
    private final TelegramBotService telegramBotService;

    public StopCommand(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void run() {
        telegramBotService.removeUpdateListener();
    }
}
