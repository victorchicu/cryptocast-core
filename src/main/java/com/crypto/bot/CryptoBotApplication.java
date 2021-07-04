package com.crypto.bot;

import com.crypto.bot.picocli.services.PicoCliService;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;

import java.util.List;

@SpringBootApplication
public class CryptoBotApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoBotApplication.class);

    private final PicoCliService picoCliService;
    private final ConversionService conversionService;
    private final TelegramBotService telegramBotService;

    public CryptoBotApplication(
            PicoCliService picoCliService,
            ConversionService conversionService,
            TelegramBotService telegramBotService
    ) {
        this.picoCliService = picoCliService;
        this.conversionService = conversionService;
        this.telegramBotService = telegramBotService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoBotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        telegramBotService.setUpdateListener((List<Update> list) -> {
            list.forEach((Update update) -> {
                try {
                    telegramBotService.saveMessage(update.message());
                    int exitCode = picoCliService.execute(update.message().text(), update);
                    LOG.info("Bot input command: {} | Execution result: {}", update.message().text(), exitCode);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> LOG.error(e.getMessage(), e));
    }
}
