package com.poc.springaidemo.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatModelController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/chatmodel")
    public String getChatModelInfo(@RequestParam(value = "message", defaultValue = "What is AI") String message) {
        return chatModel.call(message);
    }

}
