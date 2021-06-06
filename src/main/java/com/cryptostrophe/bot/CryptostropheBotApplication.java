package com.cryptostrophe.bot;

import com.cryptostrophe.bot.picocli.services.PicoCliService;
import com.cryptostrophe.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

import java.util.List;

@SpringBootApplication
public class CryptostropheBotApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CryptostropheBotApplication.class);

    private final PicoCliService picoCliService;
    private final TelegramBotService telegramBotService;

    public CryptostropheBotApplication(PicoCliService picoCliService, TelegramBotService telegramBotService) {
        this.picoCliService = picoCliService;
        this.telegramBotService = telegramBotService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptostropheBotApplication.class, args);
    }

    @Override
    public void run(String... args) {
        telegramBotService.setUpdateListener((List<Update> list) -> {
            list.forEach((Update update) -> {
                try {
                    handleUpdate(update);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> LOG.error(e.getMessage(), e));
    }

    private void handleUpdate(Update update) {
        CommandLine.ParseResult parseResult = picoCliService.parse(update.message().text());
        if (parseResult.errors().isEmpty()) {
            picoCliService.execute(update.message().text(), update);
        } else {
            for (Exception error : parseResult.errors()) {
                LOG.error(error.getMessage(), error);
            }
        }
    }
}
