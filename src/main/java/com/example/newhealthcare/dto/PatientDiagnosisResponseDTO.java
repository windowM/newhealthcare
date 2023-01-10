package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDiagnosisResponseDTO {

    private String diaDoctorId;

    private String disease;

    private String contents;

    private String diaDate;
}
