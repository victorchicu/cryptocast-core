package com.crypto.core.freemarker.services;

import com.crypto.core.BaseTest;
import com.crypto.core.binance.client.domain.event.SymbolMiniTickerEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreeMarkerTemplateServiceIT extends BaseTest {
    static String SYMBOL_FILE_NAME = "1000shibusdt.ftl";

    @Test
    public void should_render_template() {
        SymbolMiniTickerEvent symbolMiniTickerEvent = randomSymbolMiniTickerEvent();
        String template = freeMarkerTemplateService.render(SYMBOL_FILE_NAME, symbolMiniTickerEvent);
        Assertions.assertNotNull(template);
        Assertions.assertFalse(template.isEmpty());
    }
}
