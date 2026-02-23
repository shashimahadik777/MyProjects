package com.poc.springaidemo.bankingadvisorai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class Config {


    @Bean
    public ChatClient chatClient(ChatModel chatModel, BalanceTool balanceTool) {
        return ChatClient.builder(chatModel)
                .defaultTools(balanceTool) // Register tool
                .build();
    }

}
