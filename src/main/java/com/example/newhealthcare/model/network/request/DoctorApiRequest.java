package com.example.newhealthcare.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//요청은 일반 문자열
public class DoctorApiRequest {
    private String doctorId;

    private String password;

    private String email;

    private String name;

    private String phone;

    private String major;

    private String code;

}
