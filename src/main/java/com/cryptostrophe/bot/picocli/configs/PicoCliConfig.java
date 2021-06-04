package com.cryptostrophe.bot.picocli.configs;

import com.cryptostrophe.bot.picocli.commands.specific.BotCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

@Configuration
public class PicoCliConfig {
    @Bean
    public CommandLine commandLine() {
        CommandLine commandLine = new CommandLine(new BotCommand());
        commandLine.setExecutionStrategy(new CommandLine.RunAll());
        return commandLine;
    }
}
