package com.crypto.bot.telegram.services.impl;

import com.crypto.bot.telegram.configs.TelegramProperties;
import com.crypto.bot.telegram.entity.UpdateEntity;
import com.crypto.bot.telegram.repository.TelegramBotRepository;
import com.crypto.bot.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TelegramBotServiceImpl implements TelegramBotService {
    private final TelegramBot telegramBot;
    private final TelegramBotRepository telegramBotRepository;

    public TelegramBotServiceImpl(TelegramProperties telegramProperties, TelegramBotRepository telegramBotRepository) {
        this.telegramBot = new TelegramBot.Builder(telegramProperties.getToken())
                .debug()
                .okHttpClient(new OkHttpClient())
                .build();
        this.telegramBotRepository = telegramBotRepository;
    }

    @Override
    public UpdateEntity saveMessage(UpdateEntity updateEntity) {
        return telegramBotRepository.save(updateEntity);
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
    public void deleteAllMessages() {
        List<Integer> list = IterableUtils.toList(telegramBotRepository.findAll()).stream()
                .filter(updateEntity -> {
                    BaseResponse response = deleteMessage(updateEntity.getChatId(), updateEntity.getId());
                    return response.isOk();
                })
                .map(UpdateEntity::getId)
                .collect(Collectors.toList());
        telegramBotRepository.deleteAllById(list);
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
