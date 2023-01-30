package com.example.newhealthcare.model.network.request.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  //기본 생성자
@AllArgsConstructor  //모든 필드가 붙은 생성자 생성
public class DoctorDiagnosisApiRequest {

    private String doctorId;
    //private List<DoctorResponseDTO> doctorId;

    private String selPatientId;

    private String disease;

    private String contents;

    private String diaDate;

}
