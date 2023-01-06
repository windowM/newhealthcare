package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Data
public class DoctorResponseDTO {

    private String name;

    private String gender;

    private String phone;

    private String major;

    private String email;

}
