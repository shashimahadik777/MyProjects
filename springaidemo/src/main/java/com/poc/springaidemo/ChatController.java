package com.poc.springaidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private ChatClient chatClient;

public ChatController(ChatClient.Builder chatClientBuilder) {
this.chatClient=chatClientBuilder.build();
}



@GetMapping("/jokes")
public String generate(@RequestParam(value="message",defaultValue="Tell me a dad joke about computers") String message) {
return chatClient.prompt(message).call().content();

}

}
