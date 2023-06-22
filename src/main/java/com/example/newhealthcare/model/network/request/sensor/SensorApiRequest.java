package com.example.newhealthcare.model.network.request.sensor;

import com.example.newhealthcare.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorApiRequest {
    private String patientId;

    private float avgBpm;

    private float highBpm;

    private float lowBpm;

    private float avgSpo2;

    private float highSpo2;

    private float lowSpo2;

    private float avgTemp;

    private float highTemp;

    private float lowTemp;

    private String measureTime;

    private String measureDate;

    private int statusBpm;

    private int statusSpo2;

    private int statusTemp;

}
