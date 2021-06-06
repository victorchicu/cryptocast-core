package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;
import com.cryptostrophe.bot.picocli.services.BinanceService;
import com.cryptostrophe.bot.picocli.services.ParticipantSubscriptionsService;
import com.cryptostrophe.bot.picocli.services.SymbolTickerEventService;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
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
    private final SymbolTickerEventService symbolTickerEventService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public StopCommand(
            BinanceService binanceService,
            TelegramBotService telegramBotService,
            SymbolTickerEventService symbolTickerEventService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
        this.telegramBotService = telegramBotService;
        this.symbolTickerEventService = symbolTickerEventService;
        this.participantSubscriptionsService = participantSubscriptionsService;
    }

    @Override
    public void run() {
        binanceService.unsubscribeAll();
        List<ParticipantSubscriptionEntity> subscriptions = participantSubscriptionsService.findAllSubscriptions();
        for (ParticipantSubscriptionEntity subscription : subscriptions) {
            telegramBotService.deleteMessage(subscription.getChatId(), subscription.getMessageId());
            symbolTickerEventService.deleteSymbolTickerEvent(subscription.getParticipantId(), subscription.getSymbol());
            participantSubscriptionsService.deleteSubscription(subscription.getId());
        }
    }
}
