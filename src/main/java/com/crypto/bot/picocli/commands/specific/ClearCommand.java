package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.binance.services.BinanceService;
import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.services.SubscriptionsService;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
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

    private final BinanceService binanceService;
    private final TelegramBotService telegramBotService;
    private final SubscriptionsService subscriptionsService;

    @CommandLine.ParentCommand
    public BotCommand botCommand;

    public ClearCommand(
            BinanceService binanceService,
            TelegramBotService telegramBotService,
            SubscriptionsService subscriptionsService
    ) {
        this.binanceService = binanceService;
        this.telegramBotService = telegramBotService;
        this.subscriptionsService = subscriptionsService;
    }

    @Override
    public void run() {
        Update update = botCommand.getUpdate();
        binanceService.unsubscribe(update.message().from().id());
        telegramBotService.deleteAllMessages(update.message().chat().id());
        subscriptionsService.deleteAllSubscriptions(update.message().from().id());
    }
}
