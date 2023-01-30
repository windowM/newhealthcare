package com.example.newhealthcare.model.network.request.doctor;

import com.example.newhealthcare.dto.DoctorDiagnosisResponseDTO;
import com.example.newhealthcare.dto.DoctorPreResponseDTO;
import com.example.newhealthcare.model.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPreApiRequest {

    private Long preNum;

    private String contents; //특이사항 입력란

    private String preName; //약이름

    private String oneDay; //하루 복용 량

    private String total;  //총 개수

    private String preDate; //처방 날짜

    private Long diaNum;   //진단번호 참조
}
