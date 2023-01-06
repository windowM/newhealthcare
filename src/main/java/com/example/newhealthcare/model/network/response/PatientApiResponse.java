package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.dto.DoctorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientApiResponse {

    private String patientId;

    private String password;

    private String gender;

    private String born;

    private String name;

    private String phone;

    private String email;

    private String code;

    private List<DoctorResponseDTO> doctorId;

}
