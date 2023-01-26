package com.example.newhealthcare.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReservationLastInfoDTO {
    List<ReservationInfoDTO> one;
    List<ReservationInfoDTO> two;
    List<ReservationInfoDTO> three;
}
