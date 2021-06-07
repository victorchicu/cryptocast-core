package com.cryptostrophe.bot.configs;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;

public class GsonConfig {
    @Bean
    public Gson gson() {
        Gson gson = new Gson();
        return gson;
    }
}
