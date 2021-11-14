package com.xyhan.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;

@Configuration
public class AppConfig {
    @Bean("sessionStrategy")
    public SessionStrategy registryBean() {
        SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
        return sessionStrategy;
    }
}

