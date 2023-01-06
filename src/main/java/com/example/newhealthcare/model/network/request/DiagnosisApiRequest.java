package com.example.newhealthcare.model.network.request;

import com.example.newhealthcare.dto.DoctorResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiagnosisApiRequest {
    private Long diaNum;

    private List<DoctorResponseDTO> doctorId;

    private String selectPatientId;

    private String disease;

    private String contents;

    private String diaDate;
}
