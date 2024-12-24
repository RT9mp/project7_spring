package com.aivle.mini7.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log_detail")
public class LogDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logDetailId;

    @ManyToOne
    @JoinColumn(name = "log_id", nullable = false)
    private Log log;

    private String hospitalName;
    private String address;
    private String emergencyType;
    private String phoneNumber1;
    private String phoneNumber3;
    private Double latitude;
    private Double longitude;
    private Double distance;

}