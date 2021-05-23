package com.cryptostrophe.bot.services.impl;

import com.cryptostrophe.bot.exceptions.CommandLineParserException;
import com.cryptostrophe.bot.services.CommandLineParserService;
import org.apache.commons.cli.*;
import org.springframework.stereotype.Service;

@Service
public class CommandLineParserServiceImpl implements CommandLineParserService {
    private final CommandLineParser commandLineParser = new DefaultParser();

    public CommandLine parse(Options options, String... commandArgs) {
        try {
            return commandLineParser.parse(options, commandArgs);
        } catch (ParseException e) {
            throw new CommandLineParserException(e.getMessage());
        }
    }
}
