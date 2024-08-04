package com.borovkov.translator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class TranslatorConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
