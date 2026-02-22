package com.poc.springaidemo.controller;

import com.poc.springaidemo.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    ChatService service;

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @GetMapping("/jokes")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a dad joke about computers") String message) {
        return chatClient.prompt(message).call().content();

    }

    @GetMapping("/chat")
    public String ChatAssistant(@RequestParam(value = "message", defaultValue = "What is hashmap in java") String message) {
        return chatClient.prompt(message).call().content();

    }

    @GetMapping("/getinfo")
    public String getInfo(@RequestParam String msg) {
        return service.getChat(msg);
    }


}
