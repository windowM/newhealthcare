package com.example.newhealthcare.dto.dandpdto;

import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DandPResponseDTO {

    private Long seq;

    //DandP N:1 Doctor
    private List<Doctor> doctorId;

    //DandP N:1 Patient
    private List<Patient> patientId;

    private String code;

}
