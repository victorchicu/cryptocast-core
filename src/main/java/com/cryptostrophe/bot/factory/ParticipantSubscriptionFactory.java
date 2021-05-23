package com.cryptostrophe.bot.factory;

import com.cryptostrophe.bot.repository.model.ParticipantSubscription;

public class ParticipantSubscriptionFactory {
    public static ParticipantSubscription create(String symbol, Long chatId, Integer messageId, Integer participantId) {
        return new ParticipantSubscription()
                .setSymbol(symbol)
                .setChatId(chatId)
                .setMessageId(messageId)
                .setParticipantId(participantId);
    }
}
