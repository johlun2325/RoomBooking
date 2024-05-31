package com.example.roombooking.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Autowired
    private TemplateEngine templateEngine;

    @PostConstruct
    public void configureTemplateEngine() {
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setTemplateMode("HTML");
        templateEngine.addTemplateResolver(stringTemplateResolver);
    }
}
