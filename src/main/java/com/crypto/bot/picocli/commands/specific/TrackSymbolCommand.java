package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.exceptions.UnsupportedSymbolException;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.crypto.bot.binance.model.event.SymbolTickerEvent;
import com.crypto.bot.configs.BinanceProperties;
import com.crypto.bot.freemarker.services.FreeMarkerTemplateService;
import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.services.BinanceService;
import com.crypto.bot.services.ParticipantSubscriptionsService;
import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.IteratorUtils.forEach;

@Component
@CommandLine.Command(
        name = "track",
        description = "24hr rolling window mini-ticker statistics for all symbols that changed"
)
public class TrackSymbolCommand extends BaseCommand {
    private static final Logger LOG = LoggerFactory.getLogger(TrackSymbolCommand.class);

    private final BinanceService binanceService;
    private final BinanceProperties binanceProperties;
    private final TelegramBotService telegramBotService;
    private final FreeMarkerTemplateService freeMarkerTemplateService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public TrackSymbolCommand(
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            TelegramBotService telegramBotService,
            FreeMarkerTemplateService freeMarkerTemplateService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.telegramBotService = telegramBotService;
        this.freeMarkerTemplateService = freeMarkerTemplateService;
        this.participantSubscriptionsService = participantSubscriptionsService;
    }

    @CommandLine.ParentCommand
    public BotCommand botCommand;

    @CommandLine.Parameters(
            arity = "1..*",
            paramLabel = "<symbols>",
            description = "The trading 'symbol' or shortened name (typically in capital letters) that refer to a coin on a trading platform. For example: BTCUSDT"
    )
    public List<String> symbols;

    @Override
    public void run() {
        Update update = botCommand.getUpdate();
        if (usageHelpRequested) {
            String usageHelp = usage(this);
            telegramBotService.sendMessage(update.message().chat().id(), usageHelp);
        } else {
            invalidateSubscriptions(update, symbols);
            subscribeToSymbolMiniTickerEvents(update, symbols);
        }
    }

    public void invalidateSubscriptions(Update update, List<String> symbols) {
        List<ParticipantSubscriptionEntity> participantSubscriptions = participantSubscriptionsService.findSubscriptions(
                update.message().from().id(),
                symbols
        );

        forEach(participantSubscriptions.iterator(), subscription -> {
            telegramBotService.deleteMessage(
                    subscription.getChatId(),
                    subscription.getMessageId()
            );
            participantSubscriptionsService.deleteSubscription(
                    subscription.getId()
            );
        });
    }

    public void handleSymbolTickerEvent(Update update, String symbol, SymbolTickerEvent symbolTickerEvent) {
        Integer participantId = update.message().from().id();
        participantSubscriptionsService.findSubscription(participantId, symbol)
                .map(subscription -> {
                    long timeout = symbolTickerEvent.getEventTime() - (subscription.getRecentEventTime() + 5000);
                    if (timeout > 0) {
                        String templateText = renderTemplate(symbol, symbolTickerEvent);
                        telegramBotService.updateMessage(subscription.getChatId(), subscription.getMessageId(), templateText, ParseMode.HTML);
                        participantSubscriptionsService.updateSubscription(
                                Query.query(Criteria.where("symbol").is(subscription).and("participantId").is(participantId)),
                                "recentEvenType",
                                symbolTickerEvent.getEventTime()
                        );
                    }
                    return subscription;
                })
                .orElseGet(() -> {
                    String templateText = renderTemplate(symbol, symbolTickerEvent);
                    SendResponse sendResponse = telegramBotService.sendMessage(update.message().chat().id(), templateText, ParseMode.HTML);
                    participantSubscriptionsService.saveSubscription(
                            new ParticipantSubscriptionEntity()
                                    .setSymbol(symbol)
                                    .setChatId(sendResponse.message().chat().id())
                                    .setMessageId(sendResponse.message().messageId())
                                    .setParticipantId(participantId)
                                    .setRecentEventTime(symbolTickerEvent.getEventTime())
                    );
                    return null;
                });
    }

    public void subscribeToSymbolMiniTickerEvents(Update update, List<String> symbols) {
        for (String symbol : symbols) {
            binanceService.subscribeSymbolTickerEvent(
                    symbol.toLowerCase(),
                    ((SymbolTickerEvent event) -> {
                        try {
                            handleSymbolTickerEvent(update, symbol, event);
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }),
                    e -> LOG.error(e.getMessage(), e)
            );
        }
    }

    public <T> String renderTemplate(String symbol, T event) {
        return Optional.ofNullable(binanceProperties.getCryptocurrency().get(symbol))
                .map(cryptocurrency -> freeMarkerTemplateService.render(cryptocurrency.getTemplate(), event))
                .orElseThrow(() -> new UnsupportedSymbolException(symbol));
    }
}
