package com.aivle.mini7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    private String inputText;
    private Double inputLatitude;
    private Double inputLongitude;
    private int emClass;
    private Integer totalHospitals;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LogDetail> logDetails = new ArrayList<>();


}