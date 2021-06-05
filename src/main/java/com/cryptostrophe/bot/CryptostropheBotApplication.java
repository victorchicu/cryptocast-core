package com.cryptostrophe.bot;

import com.cryptostrophe.bot.services.CommandProcessor;
import com.cryptostrophe.bot.services.TelegramBotService;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
public class CryptostropheBotApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CryptostropheBotApplication.class);

    private final CommandProcessor commandProcessor;
    private final TelegramBotService telegramBotService;

    public CryptostropheBotApplication(CommandProcessor commandProcessor, TelegramBotService telegramBotService) {
        this.commandProcessor = commandProcessor;
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
                    commandProcessor.handleUpdate(update);
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> LOG.error(e.getMessage(), e));
    }
}
