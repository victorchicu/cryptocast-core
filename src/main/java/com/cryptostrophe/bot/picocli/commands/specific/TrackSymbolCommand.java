package com.cryptostrophe.bot.picocli.commands.specific;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.exceptions.UnsupportedSymbolException;
import com.cryptostrophe.bot.freemarker.services.FreeMarkerTemplateService;
import com.cryptostrophe.bot.picocli.commands.BaseCommand;
import com.cryptostrophe.bot.repository.model.ParticipantSubscriptionEntity;
import com.cryptostrophe.bot.repository.model.SymbolTickerEventEntity;
import com.cryptostrophe.bot.picocli.services.BinanceService;
import com.cryptostrophe.bot.picocli.services.ParticipantSubscriptionsService;
import com.cryptostrophe.bot.picocli.services.SymbolTickerEventService;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import com.cryptostrophe.bot.utils.BigDecimalUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.math.BigDecimal;
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

    @CommandLine.Parameters(arity = "1..*", paramLabel = "<symbols>", description = "The trading 'symbol' or shortened name (typically in capital letters) that refer to a coin on a trading platform. For example: BTCUSDT")
    public List<String> symbols;

    @Override
    public void run() {
        Update update = botCommand.getUpdate();
        if (usageHelpRequested) {
            String usageHelp = usage(this);
            telegramBotService.sendMessage(update.message().chat().id(), usageHelp);
        } else {
            trackEvents(update, symbols);
        }
    }

    private <T> String renderTemplate(String symbol, T event) {
        return Optional.ofNullable(binanceProperties.getCryptocurrency().get(symbol))
                .map(cryptocurrency -> freeMarkerTemplateService.render(cryptocurrency.getTemplate(), event))
                .orElseThrow(() -> new UnsupportedSymbolException(symbol));
    }

    private void trackEvents(Update update, List<String> symbols) {
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
        });

        if (participantSubscriptions.size() > 0) {
            participantSubscriptionsService.deleteSubscriptions(
                    participantSubscriptions.stream().map(ParticipantSubscriptionEntity::getId)
                            .collect(Collectors.toList())
            );
        }

        for (String symbol : symbols) {
            binanceService.subscribeSymbolMiniTickerEvent(
                    symbol.toLowerCase(),
                    ((SymbolMiniTickerEvent event) -> {
                        try {
                            handleSymbolMiniTickerEvent(update, symbol, event);
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }),
                    e -> LOG.error(e.getMessage(), e)
            );
        }
    }

    private void handleSymbolMiniTickerEvent(Update update, String symbol, SymbolMiniTickerEvent event) {
        String text = renderTemplate(symbol, event);
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
                    telegramBotService.updateMessage(
                            subscription.getChatId(),
                            subscription.getMessageId(),
                            text,
                            ParseMode.HTML
                    );
                    if (event.getClose().compareTo(event.getHigh()) > 0) {
                        BigDecimal percent = BigDecimalUtils.computePercentDiffBetweenTwoNumbers(event.getHigh(), event.getClose());
                        List<SymbolPrice> symbolPrices = binanceService.getSymbolPrices(event.getSymbol());
                        SymbolPrice symbolPrice = IterableUtils.first(symbolPrices);
                        telegramBotService.sendMessage(
                                subscription.getChatId(),
                                String.format("%s is up %f to $%f", event.getSymbol(), percent, symbolPrice.getPrice().toPlainString())
                        );
                    }
                    if (event.getLow().compareTo(event.getClose()) > 0) {
                        List<SymbolPrice> symbolPrices = binanceService.getSymbolPrices(event.getSymbol());
                        SymbolPrice symbolPrice = IterableUtils.first(symbolPrices);
                        BigDecimal percent = BigDecimalUtils.computePercentDiffBetweenTwoNumbers(event.getClose(), event.getLow());
                        telegramBotService.sendMessage(
                                subscription.getChatId(),
                                String.format("%s is down %f to $%f", event.getSymbol(), percent, symbolPrice.getPrice().toPlainString())
                        );
                    }
                });
                symbolTickerEventService.updateSymbolTickerEvent(participantId, symbol, event.getEventTime());
            }
        } else {
            symbolTickerEventService.saveSymbolTickerEvent(new SymbolTickerEventEntity()
                    .setSymbol(symbol)
                    .setEventTime(event.getEventTime())
                    .setParticipantId(participantId)
            );

            SendResponse sendResponse = telegramBotService.sendMessage(
                    update.message().chat().id(),
                    text,
                    ParseMode.HTML
            );

            participantSubscriptionsService.saveSubscription(new ParticipantSubscriptionEntity()
                    .setSymbol(symbol)
                    .setChatId(sendResponse.message().chat().id())
                    .setMessageId(sendResponse.message().messageId())
                    .setParticipantId(participantId)
            );
        }
    }
}
