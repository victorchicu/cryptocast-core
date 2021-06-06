package com.cryptostrophe.bot.picocli.services;

import com.pengrad.telegrambot.model.Update;
import picocli.CommandLine;

public interface PicoCliService {
    int execute(String command);
    int execute(String command, Update update);
    CommandLine.ParseResult parse(String command);
}
