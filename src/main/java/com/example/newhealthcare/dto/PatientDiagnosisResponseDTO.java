package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PatientDiagnosisResponseDTO {

    private Long diaNum;

    private String diaDate;

    private String diaDoctorId;

    private String diaContents;

    private String disease;

    private List<PatientPreResponseDTO> prescriptionList;

}
