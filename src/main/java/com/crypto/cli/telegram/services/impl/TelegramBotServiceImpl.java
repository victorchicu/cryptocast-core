package com.crypto.cli.telegram.services.impl;

import com.crypto.cli.telegram.configs.TelegramProperties;
import com.crypto.cli.telegram.entity.MessageEntity;
import com.crypto.cli.telegram.repository.TelegramBotRepository;
import com.crypto.cli.telegram.services.TelegramBotService;
import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class TelegramBotServiceImpl implements TelegramBotService {
    private final TelegramBot telegramBot;
    private final ConversionService conversionService;
    private final TelegramBotRepository telegramBotRepository;

    public TelegramBotServiceImpl(
            ConversionService conversionService,
            TelegramProperties telegramProperties,
            TelegramBotRepository telegramBotRepository
    ) {
        this.telegramBot = new TelegramBot.Builder(telegramProperties.getToken())
                .debug()
                .okHttpClient(new OkHttpClient())
                .build();
        this.conversionService = conversionService;
        this.telegramBotRepository = telegramBotRepository;
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
    public void deleteAllMessages(Long chatId) {
        List<Integer> list = IterableUtils.toList(telegramBotRepository.findAllByChatId(chatId)).stream()
                .filter((MessageEntity messageEntity) -> {
                    BaseResponse response = deleteMessage(messageEntity.getChatId(), messageEntity.getId());
                    return response.isOk();
                })
                .map(MessageEntity::getId)
                .collect(Collectors.toList());
        telegramBotRepository.deleteAllById(list);
    }

    @Override
    public SendResponse sendMessage(Long chatId, String text) {
        return call(() -> telegramBot.execute(new SendMessage(chatId, text)));
    }

    @Override
    public SendResponse sendMessage(Long chatId, String text, ParseMode parseMode) {
        return call(() -> telegramBot.execute(new SendMessage(chatId, text).parseMode(parseMode)));
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

    @Override
    public MessageEntity saveMessage(Message message) {
        MessageEntity messageEntity = conversionService.convert(message, MessageEntity.class);
        return telegramBotRepository.save(messageEntity);
    }


    private SendResponse call(Callable<SendResponse> callable) {
        SendResponse response = null;
        try {
            response = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null && response.isOk()) {
            saveMessage(response.message());
        }
        return response;
    }
}
