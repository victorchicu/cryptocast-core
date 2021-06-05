package com.cryptostrophe.bot.picocli.services.impl;

import com.cryptostrophe.bot.picocli.services.PicoCliService;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.util.Arrays;

@Service
public class PicoCliServiceImpl implements PicoCliService {
    private static final String SPACE = " ";

    private final CommandLine commandLine;

    public PicoCliServiceImpl(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public int execute(String... args) {
        return commandLine.execute(args);
    }

    @Override
    public CommandLine.ParseResult parse(String command) {
        String[] args = toArgs(command);
        commandLine.getCommandSpec().parser().collectErrors(true);
        return commandLine.parseArgs(args);
    }

    private String[] toArgs(String command) {
        String[] args = command.split(SPACE);
        if (command.startsWith("bot")) {
            args = (String[]) Arrays.stream(command.split(SPACE)).skip(1).toArray();
        }
        return args;
    }
}
