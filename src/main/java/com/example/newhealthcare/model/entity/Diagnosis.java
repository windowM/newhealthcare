package com.example.newhealthcare.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"doctorId","prescriptionList"})
@SequenceGenerator(
        name = "diagnosis_seq",
        sequenceName = "diagnosis_seq", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 10000,
        allocationSize = 1
)
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diagnosis_seq")
    @Column(name="diagnosis_num")
    private Long diaNum;

    @ManyToOne
    @JoinColumn(name="doctor")
    private Doctor doctorId;

    @Column(name="select_patient")
    private String selPatientId;

    private String disease;  //병 이름

    private String contents; //진단 내용

    @Column(name="dia_date")
    private String diaDate; //진단 날짜

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "diaNum")
    private List<Prescription> prescriptionList;
}
