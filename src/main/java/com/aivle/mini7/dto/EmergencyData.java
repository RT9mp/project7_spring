package com.aivle.mini7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyData {
    @JsonProperty("병원이름")
    private String hospitalName;

    @JsonProperty("주소")
    private String address;

    @JsonProperty("응급의료기관 종류")
    private String emergencyMedicalInstitutionType;

    @JsonProperty("전화번호 1")
    private String phoneNumber1;

    @JsonProperty("전화번호 3")
    private String phoneNumber3;

    @JsonProperty("위도")
    private double latitude;

    @JsonProperty("경도")
    private double longitude;

    @JsonProperty("거리")
    private double distance;

}