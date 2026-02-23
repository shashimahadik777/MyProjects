package com.poc.springaidemo.bankingadvisorai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/banking")
public class BankingAdvisorController {

    private final ChatClient chatClient;

    public BankingAdvisorController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/advisor")
    public String askAdvisor(@RequestParam String customerId, @RequestParam String question) {
        return chatClient.prompt()
                .user(question)
                //.tools(BalanceTool.class) // Specify the tool class to use
                //.toolNames("getBalance")
                //.tools(Map.of("customerId", customerId)) // Pass customerId
                .call()
                .content();
    }
}

