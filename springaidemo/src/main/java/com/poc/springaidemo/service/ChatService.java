package com.poc.springaidemo.service;

import com.poc.springaidemo.entity.SimpleTools;
import com.poc.springaidemo.entity.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private ChatClient chatClient;

    private WeatherTool weatherTool;

    public ChatService(ChatClient chatClient, WeatherTool weatherTool) {
        this.chatClient = chatClient;
        this.weatherTool = weatherTool;
    }

//    public String getChat(String msg) {
//        return chatClient.prompt().user(msg).call().content();
//    }

    //8. Attach the tool to the ChatClient inside service prg
    public String getChat(String msg) {
        return chatClient.prompt()
                .tools(new SimpleTools())
                .user(msg)
                .call()
                .content();

    }

    /*public String getChat(String msg) {
        return chatClient.prompt()
                .tools(new SimpleTools(), weatherTool)
                .user(msg)
                .call()
                .content();

    }*/
}
