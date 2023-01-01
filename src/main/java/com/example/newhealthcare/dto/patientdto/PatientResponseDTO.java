package com.example.newhealthcare.dto.patientdto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Builder
@Data
public class PatientResponseDTO {


    private String patientId;

    private String password;

    private String born;

    private String name;

    private String phone;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String result;

    private String content;

}
