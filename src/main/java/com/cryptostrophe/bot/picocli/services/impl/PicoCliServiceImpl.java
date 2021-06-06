package com.cryptostrophe.bot.picocli.services.impl;

import com.cryptostrophe.bot.picocli.services.PicoCliService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PicoCliServiceImpl implements PicoCliService {
    private static final String SPACE = " ";

    private final CommandLine commandLine;

    public PicoCliServiceImpl(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public int execute(String command) {
        String[] args = toArgs(command);
        return commandLine.execute(args);
    }

    @Override
    public int execute(String command, Update update) {
        return execute(command);
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
            Stream<String> stream = Arrays.stream(command.split(SPACE));
            args = stream.skip(1).collect(Collectors.toList()).toArray(new String[0]);
        }
        return args;
    }
}
