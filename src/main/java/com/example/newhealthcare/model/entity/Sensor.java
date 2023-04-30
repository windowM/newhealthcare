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
@SequenceGenerator(
        name = "customer_seq",
        sequenceName = "customer_seq",
        initialValue = 1,
        allocationSize = 1

)
//@ToString(exclude = {"patient_id"})
public class Sensor{

    @Id
    @Column(name="sensor_sequence",nullable = false)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")A
    private Long sq;

    @ManyToOne
    private Patient patientId;

    private float ecg;

    private float emg;

    private float ir;

    private float bpm;

    private float temp;

    @Column(name="sensor_date")
    private String senDate;
}
