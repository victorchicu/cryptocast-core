package com.crypto.bot.factory;

import com.crypto.bot.repository.model.ParticipantSubscriptionEntity;

public class ParticipantSubscriptionFactory {
    public static ParticipantSubscriptionEntity create(String symbol, Long chatId, Integer messageId, Integer participantId) {
        return new ParticipantSubscriptionEntity()
                .setSymbol(symbol)
                .setChatId(chatId)
                .setMessageId(messageId)
                .setParticipantId(participantId);
    }
}
