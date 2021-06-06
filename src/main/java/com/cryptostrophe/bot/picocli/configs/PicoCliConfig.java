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
    private final ApplicationContext context;

    public PicoCliConfig(@Lazy ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public CommandLine commandLine() {
        CommandLine commandLine = new CommandLine(new BotCommand(null, null), new PicocliSpringFactory(context));
        commandLine.setExecutionStrategy(new CommandLine.RunLast());
        commandLine.getCommandSpec().parser().collectErrors(true);
        return commandLine;
    }
}
