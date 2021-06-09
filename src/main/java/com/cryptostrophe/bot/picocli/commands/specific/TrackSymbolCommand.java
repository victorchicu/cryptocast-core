package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.binance.model.event.SymbolTickerEvent;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.exceptions.UnsupportedSymbolException;
import com.cryptostrophe.bot.freemarker.services.FreeMarkerTemplateService;
import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.picocli.services.BinanceService;
import com.cryptostrophe.bot.picocli.services.ParticipantSubscriptionsService;
import com.cryptostrophe.bot.picocli.services.SymbolTickerEventService;
import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;
import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final SymbolTickerEventService symbolTickerEventService;
    private final FreeMarkerTemplateService freeMarkerTemplateService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public TrackSymbolCommand(
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            TelegramBotService telegramBotService,
            SymbolTickerEventService symbolTickerEventService,
            FreeMarkerTemplateService freeMarkerTemplateService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.telegramBotService = telegramBotService;
        this.symbolTickerEventService = symbolTickerEventService;
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
            symbolTickerEventService.deleteSymbolTickerEvent(
                    subscription.getParticipantId(),
                    subscription.getSymbol()
            );
            participantSubscriptionsService.deleteSubscription(
                    subscription.getId()
            );
        });
    }

    public void handleSymbolTickerEvent(Update update, String symbol, SymbolTickerEvent event) {
        Integer participantId = update.message().from().id();
        Optional<SymbolTickerEventEntity> optional = symbolTickerEventService.findSymbolTickerEvent(participantId, symbol);
        if (optional.isPresent()) {
            SymbolTickerEventEntity symbolTickerEvent = optional.get();
            long timeout = event.getEventTime() - (symbolTickerEvent.getEventTime() + 5000);
            if (timeout > 0) {
                Optional<ParticipantSubscriptionEntity> participantSubscription = participantSubscriptionsService.findSubscription(
                        participantId,
                        symbol
                );

                participantSubscription.ifPresent(subscription -> {
                    String text = renderTemplate(symbol, event);
                    telegramBotService.updateMessage(
                            subscription.getChatId(),
                            subscription.getMessageId(),
                            text,
                            ParseMode.HTML
                    );
                });

                symbolTickerEventService.updateSymbolTickerEvent(participantId, symbol, event.getEventTime());
            }
        } else {
            String text = renderTemplate(symbol, event);

            SendResponse sendResponse = telegramBotService.sendMessage(
                    update.message().chat().id(),
                    text,
                    ParseMode.HTML
            );

            symbolTickerEventService.saveSymbolTickerEvent(new SymbolTickerEventEntity()
                    .setSymbol(symbol)
                    .setEventTime(event.getEventTime())
                    .setParticipantId(participantId)
            );

            participantSubscriptionsService.saveSubscription(new ParticipantSubscriptionEntity()
                    .setSymbol(symbol)
                    .setChatId(sendResponse.message().chat().id())
                    .setMessageId(sendResponse.message().messageId())
                    .setParticipantId(participantId)
            );
        }
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
