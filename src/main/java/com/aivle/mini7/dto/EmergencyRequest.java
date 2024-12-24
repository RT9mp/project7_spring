package com.aivle.mini7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyRequest {
    @JsonProperty("text")
    @NotNull
    private String text;

    @JsonProperty("start_lat")
    @NotNull
    private double startLat;

    @JsonProperty("start_lng")
    @NotNull
    private double startLng;

    @JsonProperty("output_count")
    @NotNull
    private int outputCount;

}