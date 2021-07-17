package com.crypto.core.freemarker.configs;

import com.crypto.core.freemarker.utils.NumberUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;

@Configuration
public class FreeMarkerConfig {
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates");
        HashMap<String, Object> sharedVariables = new HashMap<>();
        sharedVariables.put("NumberUtils", new NumberUtils());
        freeMarkerConfigurer.setFreemarkerVariables(sharedVariables);
        return freeMarkerConfigurer;
    }
}
