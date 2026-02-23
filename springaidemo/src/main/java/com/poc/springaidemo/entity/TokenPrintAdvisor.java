package com.poc.springaidemo.entity;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

import java.util.logging.Logger;

/*Custom Advisor
    - Whenever user send a request through our appl to LLM, we know that LLM will charge based on the token for input and output
    - We create custom advisor that calculates and displays the total tokens consumed
15. Create separate class which implements CallAdvisor and StreamAdvisor interface and override
        adviceCall() - logic of advisor
adviceStream() - used for streaming programing
getName()- return name of advisor
getOrder() - return execution order*/

//@Slf4j
public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor {

    private static final Logger log = Logger.getLogger(TokenPrintAdvisor.class.getName());

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
                                                 StreamAdvisorChain streamAdvisorChain) {
        Flux<ChatClientResponse> chatClientResponse = streamAdvisorChain.nextStream(chatClientRequest);
        return chatClientResponse;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        log.info("My Token Print advisor is called:");
        log.info("Request: " + chatClientRequest.prompt().getContents());
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);

        log.info("Response received from the Token advisor");
        log.info("Response: " + chatClientResponse.chatResponse().getResult().getOutput().getText());
        log.info("Prompt Token: " + chatClientResponse.chatResponse().getMetadata().getUsage().getPromptTokens()); //input token
        log.info("Completion Token: " + chatClientResponse.chatResponse().getMetadata().getUsage().getCompletionTokens()); //output token
        log.info("Total Token: " + chatClientResponse.chatResponse().getMetadata().getUsage().getTotalTokens()); //totaltoken=prompt token+completion token

        return chatClientResponse;
    }
}