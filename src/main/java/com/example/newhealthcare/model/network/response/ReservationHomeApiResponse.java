package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.dto.ReservationInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationHomeApiResponse {

    //connect 의사 정보
    private List<DoctorResponseDTO> doctor;

    //예약 정보
    private List<ReservationInfoDTO> reservationList;
}
