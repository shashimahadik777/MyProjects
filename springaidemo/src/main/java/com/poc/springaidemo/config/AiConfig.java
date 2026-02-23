package com.poc.springaidemo.config;

import com.poc.springaidemo.entity.Weather;
import com.poc.springaidemo.service.WeatherService;
import com.poc.springaidemo.service.WeatherServiceBuilder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestClient;

import java.util.function.Function;


@Configuration
public class AiConfig {
// @Autowired
 //WeatherService weatherService;

    //2. Create global chatoptions bean
    @Bean
    public OpenAiChatOptions chatOptions() {
        return OpenAiChatOptions.builder()
                .temperature(0.7)
                .maxTokens(100)
                .topP(0.9)
                .frequencyPenalty(0.4)
                .presencePenalty(0.2)
                .build();
    }

    //@Bean

//    public ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.build();
//    }


    @Bean
    public RestClient weatherRestClient() {
        return RestClient.builder().baseUrl("http://api.weatherapi.com/v1").build();
    }

    //6. We have to register this fuinction in config class
    //@Bean
    //@Description("Get the weather of the city")
    Function<Weather.Request,Weather.Response> currentWeather(WeatherService weatherService) {
        return weatherService;


    }

}

