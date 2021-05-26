package com.cryptostrophe.bot.repository;

import com.cryptostrophe.bot.BaseTest;
import com.cryptostrophe.bot.repository.model.SymbolTickerEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class SymbolTickerEventRepositoryIT extends BaseTest {
    @Test
    public void should_save_and_find_symbol_ticker_event() {
        SymbolTickerEvent expectedSymbolTickerEvent = symbolTickerEventRepository.save(randomSymbolTickerEvent());

        Optional<SymbolTickerEvent> actualSymbolTickerEvent = symbolTickerEventRepository.findSymbolTickerEvent(
                expectedSymbolTickerEvent.getParticipantId(),
                expectedSymbolTickerEvent.getSymbol()
        );

        Assertions.assertEquals(expectedSymbolTickerEvent.getId(), actualSymbolTickerEvent.get().getId());
    }
}
