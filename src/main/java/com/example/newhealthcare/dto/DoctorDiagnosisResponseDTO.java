package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDiagnosisResponseDTO {
    private Long diaNum;

    private String selectPatientId;

    private String disease;

    private String contents;

    private String diaDate;

}
