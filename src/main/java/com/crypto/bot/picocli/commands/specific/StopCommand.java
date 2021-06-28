package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.telegram.services.TelegramBotService;
import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import com.crypto.bot.services.BinanceService;
import com.crypto.bot.services.ParticipantSubscriptionsService;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;

@Component
@CommandLine.Command(
        name = "stop",
        description = "Stop Bot"
)
public class StopCommand extends BaseCommand {
    private final BinanceService binanceService;
    private final TelegramBotService telegramBotService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public StopCommand(
            BinanceService binanceService,
            TelegramBotService telegramBotService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
        this.telegramBotService = telegramBotService;
        this.participantSubscriptionsService = participantSubscriptionsService;
    }

    @Override
    public void run() {
        binanceService.unsubscribeAll();
        List<ParticipantSubscriptionEntity> subscriptions = participantSubscriptionsService.findAllSubscriptions();
        for (ParticipantSubscriptionEntity subscription : subscriptions) {
            telegramBotService.deleteMessage(subscription.getChatId(), subscription.getMessageId());
            participantSubscriptionsService.deleteSubscription(subscription.getId());
        }
    }
}
