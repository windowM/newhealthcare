package com.example.newhealthcare.model.network.request;

import com.example.newhealthcare.dto.doctordto.DoctorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientApiRequest {

    private String patientId;

    private String password;

    private String born;

    private String name;

    private String phone;

    private String email;

    private String code;

    private List<DoctorResponseDTO> doctorId;

}
