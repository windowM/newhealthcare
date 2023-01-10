package com.example.newhealthcare.model.network.response.patient;

import com.example.newhealthcare.dto.DoctorDiagnosisResponseDTO;
import com.example.newhealthcare.dto.PatientDiagnosisResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PatientDiagnosisApiResponse {
    private String patientId;

    private List<PatientDiagnosisResponseDTO> diagnosisList;
}
