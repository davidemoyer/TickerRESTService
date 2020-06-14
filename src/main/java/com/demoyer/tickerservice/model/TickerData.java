package com.demoyer.tickerservice.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

//holds the structure of the data received from tickerService, field names may vary depending on ticker history request
public class TickerData {

    private final Map<String, Map<String, String>> tickerInfoMap = new LinkedHashMap<>();
    @JsonProperty("Error Message")
    private String errorMessage;
    @JsonProperty("Information")
    private String information;
    @JsonProperty("Note")
    private String note;
    @JsonProperty("Meta Data")
    private Map<String, String> metaData;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public Map<String, Map<String, String>> getTickerInfoMap() {
        return tickerInfoMap;
    }

    //Have to use JsonAnySetter because the field names are always changing dates.
    @JsonAnySetter
    @SuppressWarnings("unchecked")
    public void setTickerInfoMap(String key, Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> dateMap = objectMapper.convertValue(object, LinkedHashMap.class);
            dateMap.forEach((dateKey, dateInfo) -> {
                Map<String, String> infoMap = objectMapper.convertValue(dateInfo, LinkedHashMap.class);
                tickerInfoMap.put(dateKey, infoMap);
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
