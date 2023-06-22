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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private Long sq;

    @ManyToOne
    private Patient patientId;

    private float avgBpm;

    private float hBpm;

    private float lBpm;

    private float avgSpo2;

    private float hSpo2;

    private float lSpo2;

    private float avgTemp;

    private float hTemp;

    private float lTemp;

    private String mTime;

    private String mDate;

    private int statusBpm;

    private int statusSpo2;

    private int statusTemp;
}
