package com.example.newhealthcare.entity;

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
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dia_num")
    private Long diaNum;

    @ManyToOne
    private Doctor doctorId;

    @Column(name="sel_patient_id")
    private String selPatientId;

    private String disease;  //병 이름

    private String contents; //진단 내용

    private String diaDate; //진단 날짜

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "diaNum")
    private List<Prescription> prescriptionList;
}
