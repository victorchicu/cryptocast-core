package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.exceptions.UnsupportedSymbolException;
import com.cryptostrophe.bot.picocli.services.PicoCliService;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.cryptostrophe.bot.services.*;
import com.cryptostrophe.bot.utils.BigDecimalUtils;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.IteratorUtils.forEach;

@Service
public class TelegramCommandProcessor implements CommandProcessor {
    private static final int SEND_TIMEOUT_TIME = 1000 * 5;
    private static final Logger LOG = LoggerFactory.getLogger(TelegramCommandProcessor.class);
    private static final String SPACE = " ";

    private final PicoCliService picoCliService;
    private final BinanceService binanceService;
    private final BinanceProperties binanceProperties;
    private final TelegramBotService telegramBotService;
    private final SymbolTickerEventService symbolTickerEventService;
    private final FreeMarkerTemplateService freeMarkerTemplateService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public TelegramCommandProcessor(
            PicoCliService picoCliService,
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            TelegramBotService telegramBotService,
            SymbolTickerEventService symbolTickerEventService,
            FreeMarkerTemplateService freeMarkerTemplateService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.picoCliService = picoCliService;
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.telegramBotService = telegramBotService;
        this.symbolTickerEventService = symbolTickerEventService;
        this.freeMarkerTemplateService = freeMarkerTemplateService;
        this.participantSubscriptionsService = participantSubscriptionsService;
    }

    @Override
    public void handleUpdate(Update update) {
        Optional<Message> optional = Optional.ofNullable(update.message());
        optional.ifPresent(message -> {
            try {
                if (!message.from().isBot()) {
                    telegramBotService.deleteMessage(message.chat().id(), message.messageId());
                }
                String[] args = toArgs(update.message().text());
                CommandLine.ParseResult parseResult = picoCliService.execute(args);
                if (!parseResult.errors().isEmpty()) {
                    Exception exception = parseResult.errors().get(0);
                    LOG.error(exception.getMessage(), exception);
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        });
    }


    private void trackEvents(List<String> symbols) {
        List<ParticipantSubscription> participantSubscriptions = participantSubscriptionsService.findSubscriptions(
                0, //TODO: update.message().from().id(),
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
                    participantSubscriptions.stream()
                            .map(ParticipantSubscription::getId)
                            .collect(Collectors.toList())
            );
        }

        for (String symbol : symbols) {
            binanceService.subscribeSymbolMiniTickerEvent(
                    symbol.toLowerCase(),
                    ((SymbolMiniTickerEvent event) -> {
                        try {
                            handleSymbolMiniTickerEvent(symbol, event);
                        } catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                    }),
                    e -> LOG.error(e.getMessage(), e)
            );
        }
    }

    private void listSymbols(Update update, List<String> symbols) {
        List<SymbolPrice> symbolPrices = binanceService.getSymbolPrices(null);

        if (symbols != null) {
            symbolPrices = symbolPrices.stream()
                    .filter(symbolPrice ->
                            symbols.stream()
                                    .anyMatch(s -> s.equals(symbolPrice.getSymbol().toLowerCase().toLowerCase()))
                    )
                    .collect(Collectors.toList());
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (SymbolPrice symbolPrice : symbolPrices) {
            stringBuilder.append(symbolPrice.getSymbol() + " " + symbolPrice.getPrice() + "\n");
        }

        telegramBotService.sendMessage(update.message().chat().id(), stringBuilder.toString());
    }

    private void stopTracking() {
        binanceService.unsubscribeAll();
        participantSubscriptionsService.findAllSubscriptions().forEach(subscription -> {
            telegramBotService.deleteMessage(subscription.getChatId(), subscription.getMessageId());
            symbolTickerEventService.deleteSymbolTickerEvent(subscription.getParticipantId(), subscription.getSymbol());
            participantSubscriptionsService.deleteSubscription(subscription.getId());
        });
    }

    private void handleSymbolMiniTickerEvent(String symbol, SymbolMiniTickerEvent event) {
        String text = renderTemplate(symbol, event);
        Integer participantId = 0; //TODO: update.message().from().id();
        Optional<SymbolTickerEvent> optional = symbolTickerEventService.findSymbolTickerEvent(participantId, symbol);
        if (optional.isPresent()) {
            SymbolTickerEvent symbolTickerEvent = optional.get();
            long timeout = event.getEventTime() - (symbolTickerEvent.getEventTime() + SEND_TIMEOUT_TIME);
            if (timeout > 0) {
                Optional<ParticipantSubscription> participantSubscription = participantSubscriptionsService.findSubscription(
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
            symbolTickerEventService.saveSymbolTickerEvent(new SymbolTickerEvent()
                    .setSymbol(symbol)
                    .setEventTime(event.getEventTime())
                    .setParticipantId(participantId)
            );

            SendResponse sendResponse = telegramBotService.sendMessage(
                    0L, //TODO: update.message().chat().id(),
                    text,
                    ParseMode.HTML
            );

            participantSubscriptionsService.saveSubscription(new ParticipantSubscription()
                    .setSymbol(symbol)
                    .setChatId(sendResponse.message().chat().id())
                    .setMessageId(sendResponse.message().messageId())
                    .setParticipantId(participantId)
            );
        }
    }

    private String[] toArgs(String command) {
        String[] args = command.split(SPACE);
        if (command.startsWith("bot")) {
            args = (String[]) Arrays.stream(command.split(SPACE)).skip(1).toArray();
        }
        return args;
    }

    private <T> String renderTemplate(String symbol, T event) {
        return Optional.ofNullable(binanceProperties.getCryptocurrency().get(symbol))
                .map(cryptocurrency -> freeMarkerTemplateService.render(cryptocurrency.getTemplate(), event))
                .orElseThrow(() -> new UnsupportedSymbolException(symbol));
    }
}