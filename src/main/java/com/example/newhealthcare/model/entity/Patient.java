package com.example.newhealthcare.model.entity;

import com.example.newhealthcare.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Builder
@Accessors(chain = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert //null 값인 field는 제외하고 삽입
//@ToString(exclude = {"dandpList","sensorList","reservationList"})
public class Patient extends BaseEntity {

    @Id
    @Column(name="patient_id", nullable = false)
    private String patientId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String born;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    //Patient 1 : N DandP
    //mappedBy= DandP테이블의 외래키
    @OneToMany(fetch=FetchType.LAZY,mappedBy = "patientId")
    private List<DandP> dandpList;

    //Patient 1 : N Sensor
    @OneToMany(fetch=FetchType.LAZY,mappedBy = "patientId")
    private List<Sensor> sensorList;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "patientId")
    private List<Reservation> reservationList;
}
