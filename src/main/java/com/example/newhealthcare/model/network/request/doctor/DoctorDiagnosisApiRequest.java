package com.example.newhealthcare.model.network.request.doctor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDiagnosisApiRequest {
    private Long diaNum;

    private String doctorId;
    //private List<DoctorResponseDTO> doctorId;

    private String selectPatientId;

    private String disease;

    private String contents;

    private String diaDate;

}
