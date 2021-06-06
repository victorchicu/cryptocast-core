package com.cryptostrophe.bot.picocli.configs;

import com.cryptostrophe.bot.picocli.commands.specific.BotCommand;
import com.pengrad.telegrambot.model.Update;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import picocli.CommandLine;
import picocli.spring.PicocliSpringFactory;

@Configuration
public class PicoCliConfig {
    private final ApplicationContext context;

    public PicoCliConfig(@Lazy ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public CommandLine commandLine() {
        CommandLine commandLine = new CommandLine(new BotCommand(new Update()), new PicocliSpringFactory(context));
        commandLine.setExecutionStrategy(new CommandLine.RunLast());
        return commandLine;
    }
}
