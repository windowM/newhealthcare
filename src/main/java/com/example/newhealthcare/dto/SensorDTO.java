package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SensorDTO {
    private String id;

    private float avg_temp;

    private float avg_bpm;

    private float avg_spo2;

}
