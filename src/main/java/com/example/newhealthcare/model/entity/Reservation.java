package com.example.newhealthcare.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"patientId"})
public class Reservation {
    @Id
    @Column(name="res_num")
    private Long resNum;

    @ManyToOne
    private Patient patientId;

    @Column(name="sel_doctor_id")
    private String selDoctorId;

    private String contents; //예약 사유

    @Column(name="res_date")
    private String resDate;

    private Long usable;
}
