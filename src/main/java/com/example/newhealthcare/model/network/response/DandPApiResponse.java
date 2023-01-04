package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DandPApiResponse {
    private Long connectSq;

    private String doctorId;

    private String patientId;

    private String code;
}
