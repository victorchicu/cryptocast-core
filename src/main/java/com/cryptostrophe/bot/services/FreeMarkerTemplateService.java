package com.cryptostrophe.bot.services;

public interface FreeMarkerTemplateService {
    String render(String name, Object model);
}
