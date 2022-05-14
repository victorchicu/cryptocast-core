package com.coinbank.core.services.impl;

import com.coinbank.core.services.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;

@Component
@SessionScope
public class ExchangeSessionDestroyer {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeSessionDestroyer.class);

    private final Map<String, ExchangeService> exchanges;

    public ExchangeSessionDestroyer(Map<String, ExchangeService> exchanges) {
        this.exchanges = exchanges;
    }

    @PreDestroy
    public void onSessionDestroy() {
        exchanges.entrySet().forEach((Map.Entry<String, ExchangeService> entry) -> {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                LOG.warn(e.getMessage(), e);
            }
        });
    }
}
