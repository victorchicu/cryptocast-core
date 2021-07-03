package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.services.SubscriptionsService;
import com.crypto.bot.telegram.services.TelegramBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "clear",
        description = "Clear chat"
)
public class ClearCommand extends BaseCommand {
    private static final Logger LOG = LoggerFactory.getLogger(ClearCommand.class);

    private final TelegramBotService telegramBotService;
    private final SubscriptionsService subscriptionsService;

    public ClearCommand(TelegramBotService telegramBotService, SubscriptionsService subscriptionsService) {
        this.telegramBotService = telegramBotService;
        this.subscriptionsService = subscriptionsService;
    }

    @Override
    public void run() {
        LOG.info("Delete all Telegram messages and subscriptions");
        telegramBotService.deleteAllMessages();
        subscriptionsService.deleteAllSubscriptions();
    }
}
