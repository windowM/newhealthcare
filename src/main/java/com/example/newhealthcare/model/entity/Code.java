package com.example.newhealthcare.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;
    //의사   1: N 코드 N : 1  환자
    private String doctorId;

    private String patientId;

    private String codeName;

    @Column(name="usable")
    private Boolean usable;//끊어진 날짜

}
