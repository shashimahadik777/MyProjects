package com.poc.springaidemo.service;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIService {

    @Autowired

    ChatModel chatModel;



    public ChatResponse getWeatherInfo(String query) {

        UserMessage msg=new UserMessage(query);

       // return chatModel.call(new Prompt(msg,OpenAiChatOptions.builder().withFunction("currentWeather").build()));
return null;
    }


}
