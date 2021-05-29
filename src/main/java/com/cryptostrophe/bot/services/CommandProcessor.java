package com.cryptostrophe.bot.services;

import com.pengrad.telegrambot.model.Update;

public interface CommandProcessor {
    void handleUpdate(Update update);
}
