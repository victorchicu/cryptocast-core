package com.cryptostrophe.bot.picocli.services.impl;

import com.cryptostrophe.bot.picocli.services.PicoCliService;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service
public class PicoCliServiceImpl implements PicoCliService {
    private final CommandLine commandLine;

    public PicoCliServiceImpl(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public CommandLine.ParseResult execute(String... args) {
        commandLine.getCommandSpec().parser().collectErrors(true);
        CommandLine.ParseResult parseResult = commandLine.parseArgs(args);
        return parseResult;
    }
}
