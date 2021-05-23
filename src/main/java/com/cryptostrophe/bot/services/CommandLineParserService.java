package com.cryptostrophe.bot.services;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public interface CommandLineParserService {
    CommandLine parse(Options options, String[] commandArgs);
}
