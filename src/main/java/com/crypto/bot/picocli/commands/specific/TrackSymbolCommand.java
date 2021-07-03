package com.crypto.bot.picocli.commands.specific;

import com.crypto.bot.binance.api.domain.event.SymbolTickerEvent;
import com.crypto.bot.binance.configs.BinanceProperties;
import com.crypto.bot.binance.services.BinanceService;
import com.crypto.bot.freemarker.services.FreeMarkerTemplateService;
import com.crypto.bot.picocli.commands.BaseCommand;
import com.crypto.bot.repository.entity.SubscriptionEntity;
import com.crypto.bot.services.SubscriptionsService;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final ConversionService conversionService;
    private final TelegramBotService telegramBotService;
    private final SubscriptionsService subscriptionsService;
    private final FreeMarkerTemplateService freeMarkerTemplateService;

    public TrackSymbolCommand(
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            ConversionService conversionService,
            TelegramBotService telegramBotService,
            FreeMarkerTemplateService freeMarkerTemplateService,
            SubscriptionsService subscriptionsService
    ) {
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.conversionService = conversionService;
        this.telegramBotService = telegramBotService;
        this.freeMarkerTemplateService = freeMarkerTemplateService;
        this.subscriptionsService = subscriptionsService;
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
            List<String> symbolNames = toBinanceFormatSymbolNames();
            invalidateSubscriptions(update, symbolNames);
            subscribeToSymbolTickerEvents(update, symbolNames);
        }
    }

    public void invalidateSubscriptions(Update update, List<String> symbolNames) {
        List<SubscriptionEntity> participantSubscriptions = subscriptionsService.findSubscriptions(
                update.message().from().id(),
                symbolNames
        );
        forEach(participantSubscriptions.iterator(), subscription -> {
            telegramBotService.deleteMessage(
                    subscription.getChatId(),
                    subscription.getMessageId()
            );
            subscriptionsService.deleteSubscription(
                    subscription.getId()
            );
        });
    }

    public void handleSymbolTickerEvent(Update update, String symbolName, SymbolTickerEvent symbolTickerEvent) {
        Integer participantId = update.message().from().id();
        Optional<SubscriptionEntity> optional = subscriptionsService.findSubscription(participantId, symbolName);
        if (optional.isPresent()) {
            SubscriptionEntity subscription = optional.get();
            long tumblingTimeWindow = (symbolTickerEvent.getEventTime() - 5000);
            if (tumblingTimeWindow > subscription.getUpdatedAt()) {
                String templateText = renderTemplate(symbolName, symbolTickerEvent);
                telegramBotService.updateMessage(
                        subscription.getChatId(),
                        subscription.getMessageId(),
                        templateText,
                        ParseMode.HTML
                );
                subscriptionsService.updateSubscription(
                        Query.query(
                                Criteria.where(SubscriptionEntity.Field.SYMBOL_NAME)
                                        .is(subscription.getSymbolName())
                                        .and(SubscriptionEntity.Field.PARTICIPANT_ID)
                                        .is(participantId)
                        ),
                        SubscriptionEntity.Field.UPDATED_AT,
                        symbolTickerEvent.getEventTime()
                );
            }
        } else {
            String templateText = renderTemplate(
                    symbolName,
                    symbolTickerEvent
            );

            SendResponse sendResponse = telegramBotService.sendMessage(
                    update.message().chat().id(),
                    templateText,
                    ParseMode.HTML
            );

            subscriptionsService.saveSubscription(
                    new SubscriptionEntity()
                            .setChatId(sendResponse.message().chat().id())
                            .setMessageId(sendResponse.message().messageId())
                            .setUpdatedAt(symbolTickerEvent.getEventTime())
                            .setSymbolName(symbolName)
                            .setParticipantId(participantId)
            );
        }
    }

    public void subscribeToSymbolTickerEvents(Update update, List<String> symbolNames) {
        for (String symbolName : symbolNames) {
            binanceService.subscribeSymbolTickerEvent(
                    symbolName.toLowerCase(),
                    ((SymbolTickerEvent symbolTickerEvent) -> {
                        try {
                            handleSymbolTickerEvent(update, symbolName, symbolTickerEvent);
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }),
                    e -> LOG.error(e.getMessage(), e)
            );
        }
    }

    private <T> String renderTemplate(String symbolName, T eventObject) {
        return freeMarkerTemplateService.render(symbolName + ".ftl", eventObject);
    }

    private List<String> toBinanceFormatSymbolNames() {
        return symbols.stream()
                .map(source -> conversionService.convert(source, String.class))
                .collect(Collectors.toList());
    }
}
