package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.dto.PatientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectListApiResponse {
    private List<DoctorResponseDTO> doctor;
    private List<PatientResponseDTO> patient;
}
