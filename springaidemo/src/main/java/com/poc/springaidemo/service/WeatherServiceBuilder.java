package com.poc.springaidemo.service;

import com.poc.springaidemo.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

//4. Create class which make call to external api (ie) weatherapi and get actual response based on Weather class

//@Service
public class WeatherServiceBuilder {

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
