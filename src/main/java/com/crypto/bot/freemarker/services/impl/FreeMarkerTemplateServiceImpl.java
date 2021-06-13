package com.crypto.bot.freemarker.services.impl;

import com.crypto.bot.freemarker.services.FreeMarkerTemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class FreeMarkerTemplateServiceImpl implements FreeMarkerTemplateService {
    private static final Logger LOG = LoggerFactory.getLogger(FreeMarkerTemplateServiceImpl.class);
    private static final String SOMETHING_WENT_WRONG_STRING = "SORRY, SOMETHING WENT WRONG";

    private final Configuration freeMarkerConfiguration;

    public FreeMarkerTemplateServiceImpl(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public String render(String name, Object model) {
        try {
            Template template = freeMarkerConfiguration.getTemplate(name);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            LOG.warn(e.getMessage(), e);
        }
        return SOMETHING_WENT_WRONG_STRING;
    }
}
