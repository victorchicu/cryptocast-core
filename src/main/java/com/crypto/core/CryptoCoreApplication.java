package com.crypto.core;

import com.crypto.core.picocli.services.PicoCliService;
import com.crypto.core.telegram.services.TelegramBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;

@SpringBootApplication
public class CryptoCoreApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoCoreApplication.class);

    private final PicoCliService picoCliService;
    private final ConversionService conversionService;
    private final TelegramBotService telegramBotService;

    public CryptoCoreApplication(
            PicoCliService picoCliService,
            ConversionService conversionService,
            TelegramBotService telegramBotService
    ) {
        this.picoCliService = picoCliService;
        this.conversionService = conversionService;
        this.telegramBotService = telegramBotService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoCoreApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        telegramBotService.setUpdateListener((List<Update> list) -> {
//            list.forEach((Update update) -> {
//                int exitCode;
//                try {
//                    telegramBotService.saveMessage(update.message());
//                } catch (Exception e) {
//                    LOG.error(e.getMessage(), e);
//                } finally {
//                    exitCode = picoCliService.execute(update.message().text(), update);
//                    LOG.info("Bot input command: {} | Execution result: {}", update.message().text(), exitCode);
//                }
//            });
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        }, e -> LOG.error(e.getMessage(), e));
    }
}
