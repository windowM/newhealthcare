package com.example.newhealthcare.model.network.response.doctor;

import com.example.newhealthcare.dto.DoctorDiagnosisResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DoctorDiagnosisApiResponse {

    private String doctorId;

    private List<DoctorDiagnosisResponseDTO> diagnosisList;

}
