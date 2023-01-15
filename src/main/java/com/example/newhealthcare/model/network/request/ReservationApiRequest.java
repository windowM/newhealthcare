package com.example.newhealthcare.model.network.request;

import com.example.newhealthcare.model.entity.Doctor;
import com.example.newhealthcare.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationApiRequest {

    private String patientId;

    private String selDoctorId;

    private String contents;

    private int resDate;

}
