package com.example.newhealthcare.dto;

import com.example.newhealthcare.model.entity.Diagnosis;
import lombok.Builder;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Builder
@Data
public class DoctorPreResponseDTO {
    private Long preNum;

    private Long diaNum;   //진단번호 참조

    private String contents; //특이사항 입력란

    private String preName; //약이름

    private String oneDay; //하루 복용 량

    private String total;  //총 개수

    private String preDate; //처방 날짜
}
