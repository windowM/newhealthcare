package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.dto.patientdto.PatientResponseDTO;
import com.example.newhealthcare.model.entity.DandP;
import com.example.newhealthcare.model.entity.Patient;
import lombok.*;

import java.util.List;

@Data
@Builder
public class DoctorHomeApiResponse {

    private String doctorId;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String major;
    private String code;
    private List<PatientResponseDTO> patientId;

}
