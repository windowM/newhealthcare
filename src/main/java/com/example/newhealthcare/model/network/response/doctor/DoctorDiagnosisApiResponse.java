package com.example.newhealthcare.model.network.response.doctor;

import com.example.newhealthcare.dto.DoctorDiagnosisResponseDTO;
import com.example.newhealthcare.dto.DoctorPreResponseDTO;
import com.example.newhealthcare.dto.PatientResponseDTO;
import com.example.newhealthcare.model.entity.Patient;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DoctorDiagnosisApiResponse {
    private Long diaNum;

    private String doctorId;

    private String selectPatientId;

    private String disease;

    private String contents;

    private String diaDate;

    private List<DoctorPreResponseDTO> prescriptionList;

}
