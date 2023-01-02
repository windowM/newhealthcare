package com.example.newhealthcare.dto.dandpdto;

import com.example.newhealthcare.entity.Doctor;
import com.example.newhealthcare.entity.Patient;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ManyToOne;

@Builder
@Data
public class DandPResponseDTO {

    //DandP N:1 Doctor
    private Doctor doctorId;

    //DandP N:1 Patient
    private Patient patientId;

    private String code;

}
