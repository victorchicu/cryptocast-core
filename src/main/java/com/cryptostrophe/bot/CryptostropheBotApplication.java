package com.cryptostrophe.bot;

import com.cryptostrophe.bot.binance.model.event.SymbolMiniTickerEvent;
import com.cryptostrophe.bot.binance.model.market.SymbolPrice;
import com.cryptostrophe.bot.factory.ParticipantSubscriptionFactory;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import com.cryptostrophe.bot.repository.model.ParticipantSubscription;
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
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CryptostropheBotApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CryptostropheBotApplication.class);
    private static final String SLASH = "/";
    private static final String LONG_DASH = "—";
    private static final String DOUBLE_DASH = "--";
    private static final String UNSUPPORTED_COMMAND_OPERATION = "Unsupported command operation";

    private final BinanceService binanceService;
    private final TelegramBotService telegramBotService;
    private final ObjectMapperService objectMapperService;
    private final CommandLineParserService commandLineParserService;
    private final SymbolTickerEventService symbolTickerEventService;
    private final ParticipantSubscriptionsService participantSubscriptionsService;


    public CryptostropheBotApplication(
            BinanceService binanceService,
            TelegramBotService telegramBotService,
            ObjectMapperService objectMapperService,
            CommandLineParserService commandLineParserService,
            SymbolTickerEventService symbolTickerEventService,
            ParticipantSubscriptionsService participantSubscriptionsService
    ) {
        this.binanceService = binanceService;
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
                                    CommandLine commandLine = commandLineParserService.parse(
                                            BotCommandOptionsBuilder.defaultOptions(),
                                            commandArgs
                                    );
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
                                            for (ParticipantSubscription participantSubscription : participantSubscriptions) {
                                                telegramBotService.deleteMessage(
                                                        participantSubscription.getChatId(),
                                                        participantSubscription.getMessageId()
                                                );
                                            }
                                            for (String symbol : symbols) {
                                                binanceService.subscribeSymbolMiniTickerEvent(
                                                        symbol.toLowerCase(),
                                                        ((SymbolMiniTickerEvent event) -> {
                                                            handleSymbolMiniTickerEvent(update, symbol, event);
                                                        }),
                                                        e -> e.printStackTrace()
                                                );
                                            }
                                        } else if (commandLine.hasOption("stop")) {
                                            binanceService.unsubscribeAll();
                                        } else {
                                            telegramBotService.sendMessage(
                                                    update.message().chat().id(),
                                                    UNSUPPORTED_COMMAND_OPERATION
                                            );
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

    private void handleSymbolMiniTickerEvent(Update update, String symbol, SymbolMiniTickerEvent symbolMiniTickerEvent) {
        Integer participantId = update.message().from().id();
        symbolTickerEventService.findSymbolTickerEvent(participantId, symbol)
                .map(symbolTickerEvent -> {
                    long timeout = symbolMiniTickerEvent.getEventTime() - (symbolTickerEvent.getEventTime() + 1000 * 60);
                    if (timeout > 0) {
                        String text = objectMapperService.serializeAsPrettyString(symbolMiniTickerEvent);
                        participantSubscriptionsService.findSubscription(participantId, symbol)
                                .map(participantSubscription -> telegramBotService.updateMessage(
                                        participantSubscription.getChatId(),
                                        participantSubscription.getMessageId(),
                                        text
                                ))
                                .orElseGet(() -> {
                                    SendResponse response = telegramBotService.sendMessage(update.message().chat().id(), text);
                                    participantSubscriptionsService.saveSubscription(ParticipantSubscriptionFactory.create(
                                            symbol,
                                            response.message().chat().id(),
                                            response.message().messageId(),
                                            response.message().from().id())
                                    );
                                    return response;
                                });
                        symbolTickerEventService.updateSymbolTickerEvent(participantId, symbolMiniTickerEvent.getEventTime());
                    }
                    return symbolTickerEvent;
                })
                .orElse(symbolTickerEventService.saveSymbolTickerEvent(
                        new SymbolTickerEvent()
                                .setSymbol(symbol)
                                .setEventTime(symbolMiniTickerEvent.getEventTime())
                                .setParticipantId(participantId))
                );
    }

    private String prepareCommand(String text) {
        String command = StringUtils.defaultString(text, "");
        return command.equals("/help") ? command.replace(SLASH, DOUBLE_DASH) : command.replace(LONG_DASH, DOUBLE_DASH);
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
}
