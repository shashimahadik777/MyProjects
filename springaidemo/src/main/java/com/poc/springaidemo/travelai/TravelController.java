package com.poc.springaidemo.travelai;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/travel")
public class TravelController {

    private final TravelItineraryService itineraryService;

    public TravelController(TravelItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @GetMapping("/itinerary")
    public String getItinerary(
            @RequestParam String destination,
            @RequestParam int days,
            @RequestParam String interests,
            @RequestParam double budget) {
        return itineraryService.generateItinerary(destination, days, interests, budget);
    }
}

