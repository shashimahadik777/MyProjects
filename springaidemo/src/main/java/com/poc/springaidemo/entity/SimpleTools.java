package com.poc.springaidemo.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class SimpleTools {

    //information tool
    @Tool(description = "Get the current date and time in users zone")
    public String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    //action tool
    @Tool(description = "Set the alarm for given time")
    public void setAlarm(@ToolParam(description = "Time in ISO-8601 format") String time) {
        var dateTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        //log.info("Set the alarm for given time.{}",dateTime);
    }

    //returnDirect - whether the tool result should be returned directly to the client instead of sending it back to LLM to compose the final response
    //default is false (ie) first it goes to LLM
    @Tool(name = "fx_rate", description = "Get current FX rate from base to quote (USD to INR)", returnDirect = true)
    public String getRate(@ToolParam(description = "Base currency code USD", required = true) String base,
                          @ToolParam(description = "Quote currency code INR", required = true) String quote) {
        return String.format("Rate %s%s=%.4f", base, quote, 85.2345);

    }

    @Tool(description = "Fetch employee info based on id")
    public String getInfo(@ToolParam(description = "id of emp")Integer id) {
        //logic to Restclient to call controller on other appl
return "Employee info for id: "+id+" Name: John Doe, Designation: Software Engineer";
    }
}
