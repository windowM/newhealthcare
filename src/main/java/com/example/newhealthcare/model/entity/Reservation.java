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
@ToString(exclude = {"patientId"})
@SequenceGenerator(
        name = "reservaiton_seq",
        sequenceName = "reservaiton_seq",
        initialValue = 1,
        allocationSize = 1
)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservaiton_seq")
    @Column(name="res_num")
    private Long resNum;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patientId;

    @Column(name="select_doctor_id")
    private String selDoctorId;

    private String contents; //예약 사유

    @Column(name="res_date")
    private String resDate;
}
