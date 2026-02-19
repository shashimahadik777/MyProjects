package com.poc.springaidemo.travelai;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TravelItineraryService {

    private final OpenAiChatModel chatModel;

    public TravelItineraryService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateItinerary(String destination, int days, String interests, double budget) {
        // Define persona with System message
        SystemMessage systemMessage = new SystemMessage(
                "You are a friendly and knowledgeable travel planner. " +
                        "You create customized itineraries based on user preferences, " +
                        "balancing cultural experiences, relaxation, and adventure."
        );

        String message = """ 
                         Create a {days}-day travel itinerary for {destination}.
                         The traveler is interested in {interests} and has a budget of {budget} INR. 
                         Provide daily recommendations with activities, meals, and highlights.
                """;
        // Define PromptTemplate with dynamic variables
        PromptTemplate template = new PromptTemplate(message);

        // Fill in variables
        String promptText = template.render(Map.of(
                "destination", destination,
                "days", String.valueOf(days),
                "interests", interests,
                "budget", String.valueOf(budget)
        ));
        UserMessage userMessage = new UserMessage(promptText);

        // Wrap both messages into a Prompt
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        // Call the model with the Prompt
        ChatResponse response = chatModel.call(prompt);
        // Extract the output text return
        return response.getResult().getOutput().getText();
    }
}

