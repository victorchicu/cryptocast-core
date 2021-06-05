package com.cryptostrophe.bot.picocli.services;

import picocli.CommandLine;

public interface PicoCliService {
    CommandLine.ParseResult execute(String... args);
}
