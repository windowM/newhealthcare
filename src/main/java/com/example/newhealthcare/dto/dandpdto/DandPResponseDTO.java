package com.example.newhealthcare.dto.dandpdto;

import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DandPResponseDTO {

    //DandP N:1 Doctor
    private Doctor doctorId;

    //DandP N:1 Patient
    private Patient patientId;

    private String code;

}
