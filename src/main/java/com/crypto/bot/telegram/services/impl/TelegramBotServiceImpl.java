package com.crypto.bot.telegram.services.impl;

import com.crypto.bot.telegram.configs.TelegramProperties;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotServiceImpl implements TelegramBotService {
    private final TelegramBot telegramBot;

    public TelegramBotServiceImpl(TelegramProperties telegramProperties) {
        this.telegramBot = new TelegramBot.Builder(telegramProperties.getToken())
                .debug()
                .okHttpClient(new OkHttpClient())
                .build();
    }

    @Override
    public void setUpdateListener(UpdatesListener updatesListener, ExceptionHandler exceptionHandler) {
        telegramBot.setUpdatesListener(updatesListener, exceptionHandler);
    }

    @Override
    public void removeUpdateListener() {
        telegramBot.removeGetUpdatesListener();
    }

    @Override
    public SendResponse sendMessage(Long chatId, String text) {
        return telegramBot.execute(new SendMessage(chatId, text));
    }

    @Override
    public SendResponse sendMessage(Long chatId, String text, ParseMode parseMode) {
        return telegramBot.execute(new SendMessage(chatId, text).parseMode(parseMode));
    }

    @Override
    public BaseResponse updateMessage(Long chatId, Integer messageId, String text) {
        return telegramBot.execute(new EditMessageText(chatId, messageId, text));
    }

    @Override
    public BaseResponse updateMessage(Long chatId, Integer messageId, String text, ParseMode parseMode) {
        return telegramBot.execute(new EditMessageText(chatId, messageId, text).parseMode(parseMode));
    }

    @Override
    public BaseResponse deleteMessage(Long chatId, Integer messageId) {
        return telegramBot.execute(new DeleteMessage(chatId, messageId));
    }
}
