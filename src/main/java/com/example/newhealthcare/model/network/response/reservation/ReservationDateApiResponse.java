package com.example.newhealthcare.model.network.response.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDateApiResponse {
    private String doctorId;
    private List<Integer> resTime;
}
