package com.poc.springaidemo.config;

import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

//2. Create global chatoptions bean
    @Bean
    public OpenAiChatOptions chatOptions() {
        return OpenAiChatOptions.builder()
                .temperature(0.7)
                .maxTokens(100)
                .topP(0.9)
                .frequencyPenalty(0.4)
                .presencePenalty(0.2)
                .build();
    }
}

