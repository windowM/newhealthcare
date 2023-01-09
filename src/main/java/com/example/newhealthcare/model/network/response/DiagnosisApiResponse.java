package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.dto.DoctorResponseDTO;
import com.example.newhealthcare.model.network.request.DiagnosisApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiagnosisApiResponse{

    private Long diaNum;

    private List<DoctorResponseDTO> doctorId;

    private String selectPatientId;

    private String disease;

    private String contents;

    private String diaDate;

}
