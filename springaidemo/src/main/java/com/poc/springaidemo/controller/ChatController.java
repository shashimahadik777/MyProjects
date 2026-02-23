package com.poc.springaidemo.controller;

import com.poc.springaidemo.entity.TokenPrintAdvisor;
import com.poc.springaidemo.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    ChatService service;

    private ChatClient chatClient;

    /*public ChatController(ChatClient.Builder builder) {

        //this.chatClient = builder.build();
        //6. Now we use SimpleLoggerAdvisor to log all the request and response on console
        //this.chatClient=builder.defaultAdvisors(new SimpleLoggerAdvisor()).build();

        //9. We implement SafeGuardAdvisor which block the call to the model provider if the user input contains any of the sensitive words, in that case it willprint default_failure_response message
        this.chatClient=builder
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new SafeGuardAdvisor(List.of("games","movies","songs")))
                .build();
    }*/


    //11. Implement chat memory so that it will maintain the conversation history using MessageChatMemoryAdvisor
    //16. Call custom  advisor in ChatClient i.e TokenPrintAdvisor
    public ChatController(ChatClient.Builder builder, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor chatMemoryAdvisor=MessageChatMemoryAdvisor.builder(chatMemory).build();
        this.chatClient=builder
                .defaultAdvisors(new TokenPrintAdvisor(),chatMemoryAdvisor,new SimpleLoggerAdvisor(),new SafeGuardAdvisor(List.of("games","movies","songs")))
                .defaultSystem("You are helpful coding assistant. You are good in coding")
                .defaultOptions(OpenAiChatOptions.builder().temperature(0.3).maxTokens(200).build())
                .build();
    }



    @GetMapping("/jokes")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a dad joke about computers") String message) {
        return chatClient.prompt(message).call().content();

    }

    @GetMapping("/chatass")
    public String ChatAssistant(@RequestParam(value = "message", defaultValue = "What is hashmap in java") String message) {
        return chatClient.prompt(message).call().content();

    }

    @GetMapping("/getinfo")
    public String getInfo(@RequestParam String msg) {
        return service.getChat(msg);
    }

    /*Advisor
    - It works like a middleware or interceptor
    - whenever user sends a request as a prompt to chatclient. First it will goto Advisor(write some cross cutting concerns). Now advisor will send the query to LLM. Finally LLM will generate the response, after that we again call advisor and then we send final response to user

    user - chatclient - Advisor - LLM - Response - Advisor - user
1. Create springboot project with web, azure ai dependency
2. Configure api key in application.properties
3. Create prompts inside resource folder
4. Create controller
5. Start the appl, run http://localhost:8080/chat?msg=json
     */

    @Value("classpath:/prompts/system-message.st")
    private Resource systemMessage;

    @Value("classpath:/prompts/user-message.st")
    private Resource userMessage;

    @GetMapping("/chat")
    public ResponseEntity<String> getMessage(@RequestParam(value = "msg", required = true) String query) {

        String str = chatClient.prompt()
                .system(system -> system.text(this.systemMessage))
                .user(user -> user.text(this.userMessage).param("concept", query))
                .call()
                .content();
        return ResponseEntity.ok(str);
    }
}
