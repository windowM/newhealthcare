package com.example.newhealthcare.model.network.response.reservation;

import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.dto.ReservationInfoDTO;
import com.example.newhealthcare.dto.ReservationLastInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationHomePApiResponse {

    //connect 의사 정보
    private List<DoctorResponseDTO> doctor;

    //예약 정보
    private List<ReservationInfoDTO> reservationList;

    //지난 예약 정보 (3개월치)
    private ReservationLastInfoDTO resLastList;
}
