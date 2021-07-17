package com.crypto.cli.picocli.services;

import com.pengrad.telegrambot.model.Update;
import picocli.CommandLine;

public interface PicoCliService {
    int execute(String command, Update... updates);
    CommandLine.ParseResult parse(String command);
}
