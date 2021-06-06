package com.cryptostrophe.bot.picocli.services;

import com.pengrad.telegrambot.model.Update;

public interface PicoCliService {
    int execute(String command, Update... updates);
}
