package com.example.newhealthcare.model.network.response.sensor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorApiResponse {
    private String patientId;

    private float avgBpm;

    private float hBpm;

    private float lBpm;

    private float avgSpo2;

    private float hSpo2;

    private float lSpo2;

    private float avgTemp;

    private float hTemp;

    private float lTemp;

    private String mTime;

    private String mDate;

    private String status;
}
