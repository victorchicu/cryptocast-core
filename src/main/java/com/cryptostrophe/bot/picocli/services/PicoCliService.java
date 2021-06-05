package com.cryptostrophe.bot.picocli.services;

import picocli.CommandLine;

public interface PicoCliService {
    int execute(String... args);
    CommandLine.ParseResult parse(String command);
}
