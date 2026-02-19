package com.poc.springaidemo.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//To get actual data from LLM
//1. Stuffing of prompt
//     - Adding our own data to the context

@RestController
public class StuffingController {

    @Autowired
    OpenAiChatModel chatClient;

    @Value("classpath:/prompts/play.st")
    private Resource promptResource1;

    @Value("classpath:/docs/sports.txt")
    private Resource contextResource;

    @GetMapping("/sports")
    public String getSporstInformation(@RequestParam(value = "message", defaultValue = "What sports are being included in the 2026 Olympics?") String message,

                                       @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffit) {

        PromptTemplate pt = new PromptTemplate(promptResource1);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);

        if (stuffit) {
            map.put("context", contextResource);
        } else {
            map.put("context", "");
        }

        Prompt p = pt.create(map);
        ChatResponse response = chatClient.call(p);

        return response.getResult().getOutput().getText();
    }
}
