package com.poc.springaidemo.service;


import com.poc.springaidemo.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

//5. Integrate service class with LLM, create a class that implements Function functional interface

//@Service
public class WeatherService implements Function<Weather.Request, Weather.Response> {


//@Autowired
//WeatherServiceBuilder serviceBuilder;

    @Override
    public Weather.Response apply(Weather.Request t) {
        return getWeather(t.city());
    }

    @Value("${spring.weather.api.base.uri}")
    private String weatherURI;

    @Value("${spring.weather.api.key}")
    private String weatherKey;

    //call weatherapi endpoint
    private RestClient restClient=RestClient.create();

    public Weather.Response getWeather(String city){
        return restClient.get()
                .uri(builder -> builder
                        .path("/current.json")
                        .queryParam("key",weatherKey)
                        .queryParam("q", city)
                        .build())
                .retrieve()
                .body(Weather.Response.class);
    }
}
