package com.cryptostrophe.bot;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.exceptions.UnsupportedSymbolException;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.cryptostrophe.bot.services.*;
import com.cryptostrophe.bot.utils.BigDecimalUtils;
import com.cryptostrophe.bot.utils.BotCommandOptionsBuilder;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.IteratorUtils.forEach;

@SpringBootApplication
public class CryptostropheBotApplication implements CommandLineRunner {
    private static final int SEND_TIMEOUT_TIME = 1000 * 5;
    private static final Logger LOG = LoggerFactory.getLogger(CryptostropheBotApplication.class);
    private static final String SLASH = "/";
    private static final String LONG_DASH = "—";
    private static final String DOUBLE_DASH = "--";

    private final BinanceService binanceService;
    private final BinanceProperties binanceProperties;
    private final TelegramBotService telegramBotService;
    private final ObjectMapperService objectMapperService;
    private final CommandLineParserService commandLineParserService;
    private final SymbolTickerEventService symbolTickerEventService;
    private final FreeMarkerTemplateService freeMarkerTemplateService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public CryptostropheBotApplication(
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            TelegramBotService telegramBotService,
            ObjectMapperService objectMapperService,
            CommandLineParserService commandLineParserService,
            SymbolTickerEventService symbolTickerEventService,
            FreeMarkerTemplateService freeMarkerTemplateService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.telegramBotService = telegramBotService;
        this.objectMapperService = objectMapperService;
        this.commandLineParserService = commandLineParserService;
        this.symbolTickerEventService = symbolTickerEventService;
        this.freeMarkerTemplateService = freeMarkerTemplateService;
        this.participantSubscriptionsService = participantSubscriptionsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptostropheBotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        telegramBotService.setUpdateListener((List<Update> list) -> {
            list.forEach((Update update) -> {
                Optional<Message> optional = Optional.ofNullable(update.message());
                optional.ifPresent(message -> {
                    try {
                        String command = prepareCommand(message.text());
                        if (!message.from().isBot()) {
                            telegramBotService.deleteMessage(message.chat().id(), message.messageId());
                        }
                        if (command.equals("--help")) {
                            help(update);
                        } else {
                            String[] commandArgs = command.split(" ");
                            Options defaultOptions = BotCommandOptionsBuilder.defaultOptions();
                            Optional.ofNullable(commandLineParserService.parse(defaultOptions, commandArgs))
                                    .ifPresent(commandLine -> {
                                        List<String> symbols = tryGetOptionArguments(commandLine, defaultOptions.getOptions());
                                        if (commandLine.hasOption("list")) {
                                            listSymbols(update, symbols);
                                        } else if (commandLine.hasOption("track")) {
                                            trackEvents(update, symbols);
                                        } else if (commandLine.hasOption("stop")) {
                                            stopTracking();
                                        } else {
                                            telegramBotService.sendMessage(update.message().chat().id(), "Unsupported command operation");
                                        }
                                    });
                        }
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                });
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> LOG.error(e.getMessage(), e));
    }

    private void help(Update update) {
        telegramBotService.sendMessage(update.message().chat().id(), helpString());
    }

    private void trackEvents(Update update, List<String> symbols) {
        List<ParticipantSubscription> participantSubscriptions = participantSubscriptionsService.findSubscriptions(
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
                            handleSymbolMiniTickerEvent(update, symbol, event);
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
        //todo: Add unsubscribe per participant subscription
        participantSubscriptionsService.findAllSubscriptions().forEach(subscription -> {
            telegramBotService.deleteMessage(subscription.getChatId(), subscription.getMessageId());
            symbolTickerEventService.deleteSymbolTickerEvent(subscription.getParticipantId(), subscription.getSymbol());
            participantSubscriptionsService.deleteSubscription(subscription.getId());
        });
    }

    private void handleSymbolMiniTickerEvent(Update update, String symbol, SymbolMiniTickerEvent event) {
        String text = renderTemplate(symbol, event);
        Integer participantId = update.message().from().id();
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
                    update.message().chat().id(),
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

    public long sizeOf(List list) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        try {
            objectOutputStream.writeObject(list);
        } finally {
            objectOutputStream.close();
        }
        return byteArrayOutputStream.toByteArray().length;
    }

    private String helpString() {
        String header = "";
        String footer = "";
        String cmdLineSyntax = "Cryptostrophe Bot commands";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        HelpFormatter formatter = new HelpFormatter();
        Options defaultOptions = BotCommandOptionsBuilder.defaultOptions();
        formatter.printHelp(printWriter, HelpFormatter.DEFAULT_WIDTH, cmdLineSyntax, header, defaultOptions, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, footer);
        return stringWriter.toString();
    }

    private String prepareCommand(String text) {
        String command = StringUtils.defaultString(text, "");
        return command.equals("/help") ? command.replace(SLASH, DOUBLE_DASH) : command.replace(LONG_DASH, DOUBLE_DASH);
    }

    private <T> String renderTemplate(String symbol, T event) {
        return Optional.ofNullable(binanceProperties.getCryptocurrency().get(symbol))
                .map(cryptocurrency -> freeMarkerTemplateService.render(cryptocurrency.getTemplate(), event))
                .orElseThrow(() -> new UnsupportedSymbolException(symbol));
    }

    private List<String> tryGetOptionArguments(CommandLine commandLine, Collection<Option> options) {
        for (Option option : options) {
            String[] optionValues = commandLine.getOptionValues(option.getOpt());
            if (optionValues == null) {
                optionValues = commandLine.getOptionValues(option.getLongOpt());
            }
            if (optionValues != null) {
                List<String> symbols = Arrays.stream(optionValues).map(String::toLowerCase).collect(Collectors.toList());
                return symbols;
            }
        }
        return null;
    }
}
