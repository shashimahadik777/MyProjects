package com.poc.springaidemo.entity;
//3. We define request and response in Weather class
public class Weather {
    public record Request(String city) { }

    public record Response(Location location, Current current) {}

    public record Location(String name, String country) {}

    public record Current(String temp_c) {}
}
