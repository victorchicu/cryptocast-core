package com.crypto.bot;

import com.crypto.bot.picocli.services.PicoCliService;
import com.crypto.bot.telegram.services.TelegramBotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;

@SpringBootApplication
public class CryptoCliApplication implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(CryptoCliApplication.class);

    private final PicoCliService picoCliService;
    private final ConversionService conversionService;
    private final TelegramBotService telegramBotService;

    public CryptoCliApplication(
            PicoCliService picoCliService,
            ConversionService conversionService,
            TelegramBotService telegramBotService
    ) {
        this.picoCliService = picoCliService;
        this.conversionService = conversionService;
        this.telegramBotService = telegramBotService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptoCliApplication.class, args);
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
