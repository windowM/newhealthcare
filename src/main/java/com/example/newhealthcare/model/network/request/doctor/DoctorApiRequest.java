package com.example.newhealthcare.model.network.request.doctor;

import com.example.newhealthcare.dto.PatientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//요청은 일반 문자열
public class DoctorApiRequest {
    private String doctorId;

    private String password;

    private String gender;

    private String email;

    private String name;

    private String phone;

    private String major;

    private String code;

    private List<PatientResponseDTO> patientId;
}
