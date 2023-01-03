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
public class Prescription{
    @Id
    @Column(name="pre_sequence",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sq;

    @ManyToOne
    private Diagnosis diaNum;   //진단번호 참조

    private String contents; //특이사항 입력란

    private String preName; //약이름

    private String oneDay; //하루 복용 량

    private String total;  //총 개수

    @Column(name="pre_date",nullable = false)
    private String preDate; //처방 날짜

}

