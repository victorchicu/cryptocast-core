package com.cryptostrophe.bot.picocli.configs;

import com.cryptostrophe.bot.picocli.commands.specific.BotCommand;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import picocli.CommandLine;
import picocli.spring.PicocliSpringFactory;

@Configuration
public class PicoCliConfig {
    private final BotCommand command;
    private final ApplicationContext context;

    public PicoCliConfig(@Lazy BotCommand command, @Lazy ApplicationContext context) {
        this.command = command;
        this.context = context;
    }

    @Bean
    public CommandLine commandLine() {
        CommandLine commandLine = new CommandLine(command, new PicocliSpringFactory(context));
        commandLine.setExecutionStrategy(new CommandLine.RunLast());
        return commandLine;
    }
}
