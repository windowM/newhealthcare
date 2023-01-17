package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservationDoctorDTO {
    private Long resNum;

    private PatientResponseDTO patientId;

    private String selDoctorId;

    private String contents; //예약 사유

    //1~8 : 9:00, 9:30, 10:00, 10:30, 14:00, 14:30, 15:00, 15:30
    private int resTime;

    private String resDate;
}
