package com.poc.springaidemo.controller;

import com.poc.springaidemo.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//    2. Function calling (used in lower version of spring AI - 1.0.6)
//      - It makes LLM decide when to call Java methods(functions) instead of just returning plain text.
//      - used with real appl logic(DB calls, API's, calculations)
//            Why?
//            - Without function calling, LLM can generate only plain text, it cannot query db, call rest api, perform secure business logic
//            - LLM decide which function to invoke and execute the function safely
@RestController
public class WeatherController {

    @Autowired

    AIService service;



    @PostMapping("/query")

    public Map<String,String> getWeatherDetails(@RequestParam String query) {

        //return Map.of("response",service.getWeatherInfo(query).getResult().getOutput().getContent());
return null;
    }

}
