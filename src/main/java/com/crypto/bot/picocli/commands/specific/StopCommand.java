package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.binance.services.BinanceService;
import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.crypto.bot.services.SubscriptionsService;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(
        name = "stop",
        description = "Stop bot"
)
public class StopCommand extends BaseCommand {
    private static final Logger LOG = LoggerFactory.getLogger(StopCommand.class);

    private final BinanceService binanceService;
    private final TelegramBotService telegramBotService;
    private final SubscriptionsService subscriptionsService;

    @CommandLine.ParentCommand
    public BotCommand botCommand;

    public StopCommand(
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
        List<SubscriptionEntity> subscriptions = subscriptionsService.findSubscriptions(update.message().from().id());
        subscriptions.forEach(entity -> binanceService.unsubscribe(entity.getParticipantId(), entity.getSymbolName()));
        telegramBotService.deleteAllMessages(update.message().chat().id());
        subscriptionsService.deleteAllSubscriptions(update.message().from().id());
    }
}
