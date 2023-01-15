package com.example.newhealthcare.model.entity;

import com.example.newhealthcare.BaseEntity;
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
public class Reservation extends BaseEntity {
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

    //1~8 : 9:00, 9:30, 10:00, 10:30, 14:00, 14:30, 15:00, 15:30
    @Column(name="res_date")
    private int resDate;
}
