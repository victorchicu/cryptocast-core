package com.cryptostrophe.bot;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.configs.BinanceProperties;
import com.cryptostrophe.bot.configs.Cryptocurrency;
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
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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
                                    telegramBotService.sendMessage(
                                            update.message().chat().id(),
                                            helpString(),
                                            ParseMode.Markdown
                                    );
                                } else {
                                    String[] commandArgs = command.split(" ");
                                    CommandLine commandLine = commandLineParserService.parse(BotCommandOptionsBuilder.defaultOptions(), commandArgs);
                                    if (commandLine != null) {
                                        if (commandLine.hasOption("list")) {
                                            String symbol = commandLine.getOptionValue("list");
                                            List<SymbolPrice> symbolPriceTickers = binanceService.getSymbolPriceTicker(symbol);
                                            String text = objectMapperService.serializeAsPrettyString(symbolPriceTickers);
                                            telegramBotService.sendMessage(update.message().chat().id(), text);
                                        } else if (commandLine.hasOption("track")) {
                                            String[] symbols = commandLine.getOptionValues("track");
                                            List<ParticipantSubscription> participantSubscriptions = participantSubscriptionsService.findSubscriptions(
                                                    update.message().from().id(),
                                                    symbols
                                            );
                                            participantSubscriptions.forEach(participantSubscription ->
                                                    telegramBotService.deleteMessage(
                                                            participantSubscription.getChatId(),
                                                            participantSubscription.getMessageId())
                                            );
                                            participantSubscriptionsService.deleteSubscriptions(
                                                    participantSubscriptions.stream()
                                                            .map(ParticipantSubscription::getId)
                                                            .collect(Collectors.toList())
                                            );
                                            for (String symbol : symbols) {
                                                binanceService.subscribeSymbolMiniTickerEvent(
                                                        symbol.toLowerCase(),
                                                        ((SymbolMiniTickerEvent event) -> handleSymbolMiniTickerEvent(update, event)),
                                                        e -> e.printStackTrace()
                                                );
                                            }
                                        } else if (commandLine.hasOption("stop")) {
                                            binanceService.unsubscribeAll();
                                        } else {
                                            telegramBotService.sendMessage(update.message().chat().id(), "Unsupported command operation");
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                LOG.error(e.getMessage(), e);
                            }
                        });
                    });
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }, e -> e.printStackTrace()
        );
    }

    private void handleSymbolMiniTickerEvent(Update update, SymbolMiniTickerEvent event) {
        String text = printSymbolMiniTickerEvent(event);
        Integer participantId = update.message().from().id();
        Optional<SymbolTickerEvent> optional = symbolTickerEventService.findSymbolTickerEvent(participantId, event.getSymbol());
        if (optional.isPresent()) {
            SymbolTickerEvent symbolTickerEvent = optional.get();
            long timeout = event.getEventTime() - (symbolTickerEvent.getEventTime() + SEND_TIMEOUT_TIME);
            if (timeout > 0) {
                Optional<ParticipantSubscription> participantSubscription = participantSubscriptionsService.findSubscription(
                        participantId,
                        event.getSymbol()
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
                                String.format("%s IS UP\n", event.getSymbol(), printSymbolMiniTickerEvent(event))
                        );
                    }
                });
                symbolTickerEventService.updateSymbolTickerEvent(participantId, event.getSymbol(), event.getEventTime());
            }
        } else {
            symbolTickerEventService.saveSymbolTickerEvent(new SymbolTickerEvent()
                    .setSymbol(event.getSymbol())
                    .setEventTime(event.getEventTime())
                    .setParticipantId(participantId)
            );

            SendResponse sendResponse = telegramBotService.sendMessage(
                    update.message().chat().id(),
                    text
            );

            participantSubscriptionsService.saveSubscription(new ParticipantSubscription()
                    .setSymbol(event.getSymbol())
                    .setChatId(sendResponse.message().chat().id())
                    .setMessageId(sendResponse.message().messageId())
                    .setParticipantId(participantId)
            );
        }
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

    private String printSymbolMiniTickerEvent(SymbolMiniTickerEvent event) {
        Cryptocurrency cryptocurrency = binanceProperties.getCryptocurrency().get(event.getSymbol());
        StringBuilder textBuilder = new StringBuilder();
        if (cryptocurrency.getDivisor() == null) {
            textBuilder = textBuilder.append("Symbol:" + event.getSymbol() + "\n");
            textBuilder = textBuilder.append("Open: " + event.getOpen() + "\n");
            textBuilder = textBuilder.append("Close: " + event.getClose() + "\n");
            textBuilder = textBuilder.append("High: " + event.getHigh() + "\n");
            textBuilder = textBuilder.append("Low: " + event.getLow() + "\n");
        } else {
            textBuilder = textBuilder.append("Symbol: " + event.getSymbol() + "\n");
            textBuilder = textBuilder.append("Open: " + event.getOpen().divide(cryptocurrency.getDivisor()) + "\n");
            textBuilder = textBuilder.append("Close: " + event.getClose().divide(cryptocurrency.getDivisor()) + "\n");
            textBuilder = textBuilder.append("High: " + event.getHigh().divide(cryptocurrency.getDivisor()) + "\n");
            textBuilder = textBuilder.append("Low: " + event.getLow().divide(cryptocurrency.getDivisor()) + "\n");
        }
        textBuilder = textBuilder.append("Total traded base asset volume: " + event.getTotalTradedBaseAssetVolume() + "\n");
        textBuilder = textBuilder.append("Total traded quote asset volume: " + event.getTotalTradedQuoteAssetVolume() + "\n");
        return textBuilder.toString();
    }
}