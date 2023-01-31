package com.example.newhealthcare.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientPreResponseDTO {

    private Long diaNum;

    private Long preNum;

    private String preName;

    private String preContents;

    private String oneDay; //하루 복용 량

    private String total;  //총 개수

    private String preDate; //처방 날짜
}
