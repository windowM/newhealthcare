package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//예약 정보 dto
public class ReservationInfoDTO {

    private Long resNum;

    private String doctorName;

    private int resTime;

    private String resDate;
}
