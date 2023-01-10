package com.example.newhealthcare.model.network.response.doctor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorPreApiResponse {

    private Long preNum;

    private String contents; //특이사항 입력란

    private String preName; //약이름

    private String oneDay; //하루 복용 량

    private String total;  //총 개수

    private String preDate; //처방 날짜

    private Long diaNum;   //진단번호 참조
}
