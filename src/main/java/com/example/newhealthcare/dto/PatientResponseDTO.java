package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Builder
@Data
public class PatientResponseDTO {

    private String gender;

    private String born;

    private String name;

    private String phone;

}
