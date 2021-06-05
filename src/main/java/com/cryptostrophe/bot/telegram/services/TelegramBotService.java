package com.cryptostrophe.bot.telegram.services;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

public interface TelegramBotService {
    void setUpdateListener(UpdatesListener updatesListener, ExceptionHandler exceptionHandler);

    void removeUpdateListener();

    SendResponse sendMessage(Long chatId, String text);

    SendResponse sendMessage(Long chatId, String text, ParseMode parseMode);

    BaseResponse updateMessage(Long chatId, Integer messageId, String text);

    BaseResponse updateMessage(Long chatId, Integer messageId, String text, ParseMode parseMode);

    BaseResponse deleteMessage(Long chatId, Integer messageId);
}
