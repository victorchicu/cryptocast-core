package com.crypto.core.picocli.commands.specific;

import com.crypto.core.exchanges.binance.services.BinanceService;
import com.crypto.core.picocli.commands.Command;
import com.crypto.core.subscriptions.services.SubscriptionsService;
import com.crypto.core.telegram.services.TelegramBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(
        name = "stop",
        description = "Stop bot"
)
public class StopCommand extends Command {
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
        /*
        Update update = botCommand.getUpdate();
        List<SubscriptionEntity> subscriptions = subscriptionsService.findSubscriptions(update.message().from().id());
        subscriptions.forEach(entity -> binanceService.unsubscribeFrom("entity.getParticipantId()", ""));
        telegramBotService.deleteAllMessages(update.message().chat().id());
        subscriptionsService.deleteAllSubscriptions(update.message().from().id());
         */
    }
}
