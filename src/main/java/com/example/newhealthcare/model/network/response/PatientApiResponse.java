package com.example.newhealthcare.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientApiResponse {

    private String patientId;

    private String password;

    private String born;

    private String name;

    private String phone;

    private String email;
}
