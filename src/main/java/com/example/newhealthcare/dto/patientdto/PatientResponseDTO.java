package com.example.newhealthcare.dto.patientdto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Builder
@Data
public class PatientResponseDTO {

    private String born;

    private String name;

    private String phone;

}
