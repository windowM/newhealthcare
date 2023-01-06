package com.example.newhealthcare.model.network.response;

import com.example.newhealthcare.dto.PatientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor//기본 생성자를 생성
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자 생성
@Builder
//응답은 암호호화 한 코드
public class DoctorApiResponse {

    private String doctorId;

    private String password;

    private String gender;

    private String email;

    private String name;

    private String phone;

    private String major;

    private String code;

    private List<PatientResponseDTO> patientId;

}
