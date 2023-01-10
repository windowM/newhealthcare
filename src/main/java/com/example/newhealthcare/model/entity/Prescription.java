package com.example.newhealthcare.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"diaNum"})
@SequenceGenerator(
        name = "prescription_seq",
        sequenceName = "prescription_seq", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 50000,
        allocationSize = 1
)
public class Prescription{
    @Id
    @Column(name="pre_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prescription_seq")
    private Long preNum;

    @ManyToOne
    @JoinColumn(name="diagnosis_num")
    private Diagnosis diaNum;   //진단번호 참조

    private String contents; //특이사항 입력란

    private String preName; //약이름

    private String oneDay; //하루 복용 량

    private String total;  //총 개수

    private String preDate; //처방 날짜

}

