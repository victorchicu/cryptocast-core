package com.cryptostrophe.bot;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.exceptions.UnsupportedSymbolException;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.cryptostrophe.bot.services.*;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final ParticipantSubscriptionsService participantSubscriptionsService;

    public CryptostropheBotApplication(
            BinanceService binanceService,
            BinanceProperties binanceProperties,
            TelegramBotService telegramBotService,
            ObjectMapperService objectMapperService,
            CommandLineParserService commandLineParserService,
            SymbolTickerEventService symbolTickerEventService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
        this.binanceProperties = binanceProperties;
        this.telegramBotService = telegramBotService;
        this.objectMapperService = objectMapperService;
        this.commandLineParserService = commandLineParserService;
        this.symbolTickerEventService = symbolTickerEventService;
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
                        if (command.equals("--help")) {
                            printHelp(update);
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
                                            stopTracking(update);
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
        }, e -> e.printStackTrace());
    }

    private void printHelp(Update update) {
        telegramBotService.sendMessage(
                update.message().chat().id(),
                helpString(),
                ParseMode.Markdown
        );
    }

    private void trackEvents(Update update, List<String> symbols) {
        List<ParticipantSubscription> participantSubscriptions = participantSubscriptionsService.findSubscriptions(
                update.message().from().id(),
                symbols
        );

        participantSubscriptions.forEach(participantSubscription ->
                telegramBotService.deleteMessage(
                        participantSubscription.getChatId(),
                        participantSubscription.getMessageId()
                )
        );

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
                            e.printStackTrace();
                        }
                    }),
                    e -> e.printStackTrace()
            );
        }
    }

    private void listSymbols(Update update, List<String> symbols) {
        List<SymbolPrice> symbolPriceTickers = binanceService.getSymbolPriceTicker(symbols.size() == 0 ? null : symbols.get(0));
        try {
            //todo: Split list to multipart message (telegram message max size is 4096)
            long sizeOf = sizeOf(symbolPriceTickers);
            if (sizeOf <= 4096) {
                String text = objectMapperService.serializeAsPrettyString(symbolPriceTickers);
                telegramBotService.sendMessage(update.message().chat().id(), text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopTracking(Update update) {
        telegramBotService.sendMessage(update.message().chat().id(), "Going to stop all tracked subscriptions..");
        binanceService.unsubscribeAll();
        telegramBotService.sendMessage(update.message().chat().id(), "DONE");
    }

    private void handleSymbolMiniTickerEvent(Update update, String symbol, SymbolMiniTickerEvent event) {
        String text = renderSymbolMiniTickerEvent(symbol, event);
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
                            text
                    );
                    if (event.getClose().compareTo(event.getHigh()) > 0) {
                        telegramBotService.sendMessage(
                                subscription.getChatId(),
                                String.format("%s IS UP\n%s", symbol, renderSymbolMiniTickerEvent(symbol, event))
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
                    text
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

    private String renderSymbolMiniTickerEvent(String symbol, SymbolMiniTickerEvent event) {
        //todo: Add free market template
        return Optional.ofNullable(binanceProperties.getCryptocurrency().get(symbol))
                .map(cryptocurrency -> {
                    StringBuilder textBuilder = new StringBuilder();
                    if (cryptocurrency.getDivisor() == null) {
                        textBuilder = textBuilder.append("Symbol: " + symbol + "\n");
                        textBuilder = textBuilder.append("Open: " + event.getOpen() + "\n");
                        textBuilder = textBuilder.append("Close: " + event.getClose() + "\n");
                        textBuilder = textBuilder.append("High: " + event.getHigh() + "\n");
                        textBuilder = textBuilder.append("Low: " + event.getLow() + "\n");
                    } else {
                        textBuilder = textBuilder.append("Symbol: " + symbol + "\n");
                        textBuilder = textBuilder.append("Open: " + event.getOpen().divide(cryptocurrency.getDivisor()) + "\n");
                        textBuilder = textBuilder.append("Close: " + event.getClose().divide(cryptocurrency.getDivisor()) + "\n");
                        textBuilder = textBuilder.append("High: " + event.getHigh().divide(cryptocurrency.getDivisor()) + "\n");
                        textBuilder = textBuilder.append("Low: " + event.getLow().divide(cryptocurrency.getDivisor()) + "\n");
                    }
                    textBuilder = textBuilder.append("Total traded base asset volume: " + event.getTotalTradedBaseAssetVolume() + "\n");
                    textBuilder = textBuilder.append("Total traded quote asset volume: " + event.getTotalTradedQuoteAssetVolume() + "\n");
                    return textBuilder.toString();
                })
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