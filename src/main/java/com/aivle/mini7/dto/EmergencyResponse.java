package com.aivle.mini7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class EmergencyResponse {
    @JsonProperty("text")
    private String text;

    @JsonProperty("grade")
    private int grade;

    @JsonProperty("start_lat")
    private double start_lat;

    @JsonProperty("start_lng")
    private double start_lng;

    @JsonProperty("emergency_data")
    private List<EmergencyData> emergency_data;

}