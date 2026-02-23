package com.poc.springaidemo.hradvisorai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hr/")
public class AdvisorController {

    private final ChatClient chatClient;

    public AdvisorController(ChatClient.Builder builder, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient = builder
                .defaultAdvisors(chatMemoryAdvisor,
                        new SimpleLoggerAdvisor(),
                        new SafeGuardAdvisor(List.of("salary", "compensation", "bonus", "games", "movies", "songs", "dating")))
                .defaultSystem("You are a helpful HR coding assistant.").build();
    }

    @GetMapping("/chatadvisor")
    public ResponseEntity<String> chatAdvisor(@RequestParam("msg") String query) {
        String response = chatClient.prompt()
                .user(query)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

/*
    Handson
    You are developing an AI Advisor for a corporate HR department. The advisor must meet 3 requirements
1. It must remember the users previous questions to handle follow up queries
2. All raw prompts and model responses must be logged for internal review
3. It must block any questions related to internal salary data or non-work topics
*/

}

