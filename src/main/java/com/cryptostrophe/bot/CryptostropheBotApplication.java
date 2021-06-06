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
                    String command = update.message().text();
                    int exitCode = picoCliService.execute(command, update);
                    if (exitCode > 0) {
                        //TODO: Do parse and collect errors
                    }
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> LOG.error(e.getMessage(), e));
    }
}
