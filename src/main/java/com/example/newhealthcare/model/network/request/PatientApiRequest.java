package com.example.newhealthcare.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

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
}
